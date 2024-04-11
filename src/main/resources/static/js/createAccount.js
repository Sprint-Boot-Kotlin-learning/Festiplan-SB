$(function () {
    $("#btnMDP").click(function () {
        if ($("#MDP").attr("type") === "text") {
            $("#MDP").attr("type", "password");
        } else {
            $("#MDP").attr("type", "text");
        }
    });

    $("#btnConfirmerMDP").click(function () {
        if ($("#confirmerMDP").attr("type") === "text") {
            $("#confirmerMDP").attr("type", "password");
        } else {
            $("#confirmerMDP").attr("type", "text");
        }
    });
});