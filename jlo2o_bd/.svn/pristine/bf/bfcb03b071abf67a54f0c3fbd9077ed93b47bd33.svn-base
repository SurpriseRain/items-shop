
        var _jBoxConfig = {};
        _jBoxConfig.defaults = {
            id: null, /* 在页面中的唯一ID，如果为null则自动为随机ID,一个ID只会显示一个jBox */
            top: '30%', /* 窗口离顶部的距离,可以是百分比或像素(如 '100px') */
            border: 5, /* 窗口的外边框像素大小,必须是0以上的整数 */
            opacity: 0.2, /* 窗口隔离层的透明度,如果设置为0,则不显示隔离层 */
            timeout: 0, /* 窗口显示多少毫秒后自动关闭,如果设置为0,则不自动关闭 */
            showClose: true, /* 是否显示窗口右上角的关闭按钮 */
            draggable: true, /* 是否可以拖动窗口 */
            dragLimit: true, /* 在可以拖动窗口的情况下，是否限制在可视范围 */
            dragClone: false, /* 在可以拖动窗口的情况下，鼠标按下时窗口是否克隆窗口 */
            persistent: true, /* 在显示隔离层的情况下，点击隔离层时，是否坚持窗口不关闭 */
            showScrolling: true, /* 是否显示浏览的滚动条 */
            ajaxData: {},  /* 在窗口内容使用post:前缀标识的情况下，ajax post的数据，例如：{ id: 1 } 或 "id=1" */
            iframeScrolling: 'auto', /* 在窗口内容使用iframe:前缀标识的情况下，iframe的scrolling属性值，可选值有：'auto'、'yes'、'no' */

            width: 'auto', /* 窗口的宽度，值为'auto'或表示像素的整数 */
            height: 'auto', /* 窗口的高度，值为'auto'或表示像素的整数 */
            bottomText: '', /* 窗口的按钮左边的内容，当没有按钮时此设置无效 */
            buttons: { '确定': 'ok' }, /* 窗口的按钮 */
            buttonsFocus: 0, /* 表示第几个按钮为默认按钮，索引从0开始 */
            loaded: function (h) { }, /* 窗口加载完成后执行的函数，需要注意的是，如果是ajax或iframe也是要等加载完http请求才算窗口加载完成，参数h表示窗口内容的jQuery对象 */
            submit: function (v, h, f) { return true; }, /* 点击窗口按钮后的回调函数，返回true时表示关闭窗口，参数有三个，v表示所点的按钮的返回值，h表示窗口内容的jQuery对象，f表示窗口内容里的form表单键值 */
            closed: function () { } /* 窗口关闭后执行的函数 */
        };
        $.jBox.setDefaults(_jBoxConfig);