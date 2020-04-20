function post() {
    var questionId = $("#question_id").val();
    var commentCotent = $("#comment_content").val();

    if (!commentCotent) {
        alert("Comment can't be blank!");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": questionId,
            "content": commentCotent,
            "type": 1
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
                        window.localStorage.setItem("questionId", questionId);
                        window.localStorage.setItem("commentContent", commentCotent);
                        window.close();
                    }
                } else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}