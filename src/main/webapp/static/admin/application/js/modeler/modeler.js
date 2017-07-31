/**
 * Created by chenguojun on 8/10/16.
 */
;
(function ($, window, document, undefined) {
    /**
     * 功能菜单 对应 当前的唯一别名
     * @type {{/api/sys/user/pageList: string}}
     */
    var mapping = {
        "/modeler/list": "modelerlist"
    }
    /**
     * 加入全局mapping
     */
    App.requestMapping = $.extend({}, window.App.requestMapping, mapping);
    /**
     * 对应requestMapping值 sysUser page函数为进入页面入口方法
     * @type {{page: App.sysUser.page}}
     */
    App.modelerlist = {
        page: function (title) {
            window.App.content.empty();
            window.App.title(title);
            var content = $('<div class="panel-body" id="moder_grid"></div>');
            window.App.content.append(content);
            App.modelerlist.initEvents();
        }
    }
    /**
     * 初始化事件
     */
    App.modelerlist.initEvents = function () {
        var grid;
        var options = {
            url: App.href + "/modeler/list",
            beforeSend: function (request) {
                request.setRequestHeader("X-Auth-Token", App.token);
            },
            pageNum: 1,//当前页码
            pageSize: 15,//每页显示条数
            idFiled: "id",//id域指定
            showCheckbox: true,//是否显示checkbox
            checkboxWidth: "3%",
            showIndexNum: true,
            indexNumWidth: "5%",
            pageSelect: [2, 15, 30, 50],
            columns: [{
                title: "key",
                field: "key",
                sort: true
            }, {
                title: "catagory",
                field: "catagory"
            }, {
                title: "流程名",
                field: "name"
            },
            {
                title: "发布Id",
                field: "deploymentId"
            },
            {
                title: "创建时间",
                field: "createTime"
            },
            {
                title: "上次更新时间",
                field: "lastUpdateTime"
            }
            ],
            actionColumnText: "操作",//操作列文本
            actionColumnWidth: "35%",
            actionColumns: [{
                text: "编辑",
                cls: "btn-primary btn-sm",
                handle: function (index, data) {
                	window.open(App.href+"/modeler/modeler-open?id="+data.id)
                 }
                },
               {
                text: "删除",
                cls: "btn-danger btn-sm",
                handle: function (index, data) {
                    var requestUrl = App.href + "/modeler/modeler-remove";
                    $.ajax({
                        type: "POST",
                        beforeSend: function (request) {
                            request.setRequestHeader("X-Auth-Token", App.token);
                        },
                        dataType: "json",
                        data: {
                            id: data.id
                        },
                        url: requestUrl,
                        success: function (data) {
                            if (data.code === 200) {
                                grid.reload();
                            } else {
                                alert(data.message);
                            }
                        },
                        error: function (e) {
                            alert("请求异常。");
                        }
                    });
                }
            }, {
	           	 text: "发布",
	             cls: "btn-primary btn-sm",
	             handle: function (index, data) {
	            	 $.ajax({
	            		 url:App.href+"/modeler/modeler-deploy",
	            		 beforeSend: function (request) {
	                            request.setRequestHeader("X-Auth-Token", App.token);
	                        },
	            		 data:{id:data.id},
	            		 success:function(result)
	            		 {
	            			 alert(result.message);
	            		 }
	            	 })
	              }
               }
            ],
            tools: [
                {
                    text: " 新建模型",//按钮文本
                    cls: "btn btn-primary",//按钮样式
                    icon: "fa fa-cubes",
                    handle: function (grid) {
                        window.open(App.href+"/modeler/create");
                    }
                }
            ]
        };
        grid = window.App.content.find("#moder_grid").topieGrid(options);
    }
})(jQuery, window, document);
