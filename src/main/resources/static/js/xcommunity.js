function post() {
    var questionId = $("#question_id").val();
    var commentCotent = $("#comment_content").val();
    comment2target(questionId, 1, commentCotent);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("Comment can't be blank!");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccpted = confirm(response.message);
                    if (isAccpted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=e4e04b0f040f6502848b&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                        window.localStorage.setItem("questionId", targetId);
                        window.localStorage.setItem("commentContent", content);
                        window.close();
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute('data-id');
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        comments.removeClass("show");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            comments.addClass("show");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left "
                    }).append($("<img/>", {
                        "class": "mr-3",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<a/>", {
                        "html": comment.user.name,
                        "href": ""
                    })).append($("<span/>", {
                        "class": "text-desc",
                        "html": moment(comment.gmtCreate).format(' YYYY-MM-DD')
                    }).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media comment"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": " comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("show in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}