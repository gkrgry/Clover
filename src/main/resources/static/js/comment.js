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

        $.getJSON(`/comments/pages/${boardId}/${pageNum}`,
            function (data){
            if(callback){
                // callback(data);
                callback(data.commentCnt, data.list)
            }
            }).fail(function (xhr, status, err){
                if(error){
                error();
                }
        })
    }

    function remove(commentId, callback, error){
        $.ajax({
            type: 'delete',
            url : '/comments/' + commentId,
            success: function (deleteResult, status, xhr){
                if(callback)[
                    callback(deleteResult)
                ]
            },
            error : function (xhr, status, er){
                if(error){
                    error(er)
                }
            }
        })
    }

    function update(comment,callback,error){
        console.log("commentId " + comment.commentId);

        $.ajax({
            type: 'put',
            url: '/comments/'+comment.commentId,
            data: JSON.stringify(comment),
            contentType: "application/json;charset=utf-8",
            success: function (result, status, xhr){
                if(callback){
                    callback(result);
                }
            },
            error: function (xhr,status,er){
                if(error){
                    error(er);
                }
            }
        })
    }

    function get(commentId,callback, error){
        $.getJSON(`/comments/+${commentId}`, function (result){
            if(callback){
                callback(result);
            }
        }).fail(function (xhr, status,err){
            if(error){
                error();
            }
        });
    }
    function displayTime (timeValue){
        var today = new Date();

        var gap = today.getTime() - timeValue;

        var dataObj = new Date(timeValue);
        var str = "";

        if(gap < (1000 * 60 * 60 *24)){
            var hh = dataObj.getHours();
            var mi = dataObj.getMinutes();
            var ss = dataObj.getSeconds();
            return[ (hh>9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi, ':',(ss>9 ? '' : '0') + ss].join('');
        }else{
            var yy = dataObj.getFullYear();
            var mm = dataObj.getMonth() + 1;
            var dd = dataObj.getDate();

            return [yy,'/',(mm>9?'':'0') + mm,'/',(dd>9?'':'0')+dd].join('');
        }
    }



    return {
        add: add,
        getList : getList,
        remove : remove,
        update : update,
        get : get,
        displayTime : displayTime
    };
})();