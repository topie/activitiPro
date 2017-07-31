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
        "/bussProcess/list": "bussProcess"
    }
    /**
     * 加入全局mapping
     */
    App.requestMapping = $.extend({}, window.App.requestMapping, mapping);
    /**
     * 对应requestMapping值 sysUser page函数为进入页面入口方法
     * @type {{page: App.sysUser.page}}
     */
    App.bussProcess = {
        page: function (title) {
            window.App.content.empty();
            window.App.title(title);
            var content = $('<div class="panel-body" id="bussProcess_grid"></div>');
            window.App.content.append(content);
            App.bussProcess.initEvents();
        }
    }
    /**
     * 初始化事件
     */
    var grid;
    App.bussProcess.formItems = {
            id: "buss_form",//表单id
            name: "buss_form",//表单名
            method: "POST",//表单method
            action: App.href + "/bussProcess/save",//表单action
            ajaxSubmit: true,//是否使用ajax提交表单
            beforeSend: function (request) {
                request.setRequestHeader("X-Auth-Token", App.token);
            },
            ajaxSuccess: function () {
                modal.hide();
                grid.reload();
            },
            submitText: "保存",//保存按钮的文本
            showReset: true,//是否显示重置按钮
            resetText: "重置",//重置按钮文本
            isValidate: true,//开启验证
            buttons: [{
                type: 'button',
                text: '关闭',
                handle: function () {
                    modal.hide();
                }
            }],
            buttonsAlign: "center",
            items:[
			{
			type: 'hidden',
			name: 'id',
			id: 'id'
			},
            {
            type: 'text',
            name: 'name',
            label: '名称'
            }, 
            {
            type: 'text',
            name: 'describtion',
            label: '描述'
            },
            {
                type: 'select',
                name: 'processId',
                label: '流程',
                items: [
                        {
                            text: "请选择角色",
                            value: ""
                        }
                    ],
                itemsUrl: App.href + "/modeler/listAll?topie_token=" + App.token            
            }
            ]
    };
    
    App.bussProcess.nodeOptions = {
            url: App.href + "/modeler/node-list",
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
                title: "名称",
                field: "nodeName",
                sort: true
            },
            {
                title: "类型",
                field: "nodeType",
                sort: true
            }
            ],
            actionColumnText: "操作",//操作列文本
            actionColumnWidth: "35%",
            actionColumns: [{
                text: "配置用户",
                cls: "btn-primary btn-sm",
                handle: function (index, data) {
                	modal = $.topieModal({
                        id: "bussModel",
                        title: "编辑",
                        destroy: true
                    });
                	 var form = modal.$body.topieForm(App.bussProcess.formItems);
                     form.loadRemote(App.href + "/bussProcess/load?id=" + data.id);
                     modal.show();
                }
            }],
            tools: [
                {
                    text: " 新建",//按钮文本
                    cls: "btn btn-primary",//按钮样式
                    icon: "fa fa-cubes",
                    handle: function (grid) {
                    	modal = $.topieModal({
                            id: "bussModel",
                            title: "新建",
                            destroy: true
                        });
                    	 var form = modal.$body.topieForm(App.bussProcess.formItems);
                         modal.show();
                    }
                }
                ]
        };
    
    App.bussProcess.initEvents = function () {
        var options = {
            url: App.href + "/bussProcess/list",
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
                title: "名称",
                field: "name",
                sort: true
            }, {
                title: "描述",
                field: "describtion"
            }, {
                title: "流程Id",
                field: "processId"
            }
            ],
            actionColumnText: "操作",//操作列文本
            actionColumnWidth: "35%",
            actionColumns: [{
                text: "编辑",
                cls: "btn-primary btn-sm",
                handle: function (index, data) {
                	modal = $.topieModal({
                        id: "bussModel",
                        title: "编辑",
                        destroy: true
                    });
                	 var form = modal.$body.topieForm(App.bussProcess.formItems);
                     form.loadRemote(App.href + "/bussProcess/load?id=" + data.id);
                     modal.show();
                }
            },
               {
                text: "删除",
                cls: "btn-danger btn-sm",
                handle: function (index, data) {
                    var requestUrl = App.href + "/bussProcess/delete";
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
            },
            {
	           	 text: "配置",
	             cls: "btn-primary btn-sm",
	             handle: function (index, data) {
	            	 $("#bussProcess_grid").html("");	 
		               App.bussProcess.nodeOptions.url = App.href + "/modeler/node-list?id="+data.processId;
		               grid = window.App.content.find("#bussProcess_grid").topieGrid(App.bussProcess.nodeOptions); 
	              }
              }
            ],
            tools: [
                {
                    text: " 新建",//按钮文本
                    cls: "btn btn-primary",//按钮样式
                    icon: "fa fa-cubes",
                    handle: function (grid) {
                    	modal = $.topieModal({
                            id: "bussModel",
                            title: "新建",
                            destroy: true
                        });
                    	 var form = modal.$body.topieForm(App.bussProcess.formItems);
                         modal.show();
                    }
                }
                ]
        };
        grid = window.App.content.find("#bussProcess_grid").topieGrid(options);
    }
})(jQuery, window, document);
