$(function () {
    jQuery.validator.addMethod("mobile", function(value, element) {
        var length = value.length;
        var regPhone = /^1([3578]\d|4[57])\d{8}$/;
        return this.optional(element) || ( length == 11 && regPhone.test( value ) );
    }, "请正确填写您的手机号码");
});