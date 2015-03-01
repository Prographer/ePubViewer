$(document).ready(function () {
    $(".toc li").on("click", function () {
        var url = $(this).data("toc-url");
        $('#view-frame').attr('src', url);
    });
});