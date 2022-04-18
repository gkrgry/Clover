var commentService = (function (){
    function add(comment, callback,error){
        console.log("add comment");

        $.ajax({
            type: 'post',
            url: '/comments/new',
            data: JSON.stringify(comment),
            contentType : "application/json; charset=urf-8",
            success : function (result, status, xhr){
                if(callback){
                    callback(result);
                }
            },
            error : function (xhr, status, er){
                if(error){
                    error(er);
                }
            }
        })
    }

    function getList (param, callback, error){
        var boardId = param.boardId;
        var pageNum = param.pageNum || 1;
        var pageNumS = String(pageNum)
        console.log("type " + typeof boardId);
        console.log("type " + typeof pageNum);
        console.log("type " + typeof pageNum);

        $.getJSON(`/comments/pages/${boardId}/${pageNum}`,
            function (data){
            if(callback){
                callback(data);
            }
            }).fail(function (xhr, status, err){
                if(error){
                error();
                }
        })
    }

    return {
        add: add,
        getList : getList
    };
})();