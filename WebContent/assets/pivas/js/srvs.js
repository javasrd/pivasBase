var percent = 0;
$(function () {
    $(".tbl tbody tr").each(function (i, val) {
        if (i % 2 == 0) {
            $(this).css("background-color", "#fff");
        } else {
            $(this).css("background-color", "#ededed");
        }
    });

    $(".cure-detail").each(function (i, val) {
        $(this).children().each(function (i, val) {
            if (i % 2 == 0) {
                $(this).css("background-color", "#f5f5f5");
            } else {
                $(this).css("background-color", "#ededed");
            }
        });
    });
    $(".detail-result textarea").focus(function () {
        $(this).css("background-color", "#EEC4C5");
    }).blur(function () {
        $(this).css("background-color", "#EDEDED");
    });
    $(".checkbox").click(function () {
        var clazz = $(this).attr("class");
        if (clazz.indexOf("checked") > -1) {
            $(this).removeClass("checked");
        } else {
            $(this).addClass("checked");
        }
    });
    $(".tabs-content table tbody").each(function () {
        var $this = $(this);

        $this.children().each(function (i, val) {
            if (i % 2 == 0) {
                $(this).css("background-color", "#f5f5f5");
            } else {
                $(this).css("background-color", "#ededed");
            }

        });
    });

    $(".medicine-tab .tabs-title a").click(function () {
        $(".medicine-tab .tabs-title a").each(function () {
            $(this).removeClass("select");
        });
        $(".medicine-tab .tabs-content").children().each(function () {
            $(this).css("display", "none");
        });

        var currentClazz = $(this).attr("class");
        var no = currentClazz.replace("tab-title-", "");
        $(".tab-content-" + no).css("display", "block");

        $(this).addClass("select");
    });

    $("table.narrow-table  tbody").each(function () {
        var $this = $(this);

        $this.children().each(function (i, val) {
            if (i % 2 == 0) {
                $(this).css("background-color", "#f5f5f5");
            } else {
                $(this).css("background-color", "#ededed");
            }

        });
    });
});






















