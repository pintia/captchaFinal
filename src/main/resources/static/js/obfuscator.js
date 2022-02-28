var JavaScriptObfuscator = require('javascript-obfuscator');
var fs = require('fs');
const args = process.argv.slice(2);
var inputPath = args[0];
var url = args[1];
var scale = args[2];

if (!String.prototype.format) {
    String.prototype.format = function() {
        var args = arguments;
        return this.replace(/{(\d+)}/g, function(match, number) {
            return typeof args[number] != 'undefined'
                ? args[number]
                : match
                ;
        });
    };
}

fs.readFile(inputPath, 'utf8' , (err, data) => {
    if (err) {
        console.error(err)
        return
    }
    var file = data.format(scale, url);
    var obfuscationResult = JavaScriptObfuscator.obfuscate(file, {
        controlFlowFlattening: true, //影响性能
        deadCodeInjection: true,//影响代码量和文件大小
        debugProtection: false,//影响开发者工具
        debugProtectionInterval: 4000,
        disableConsoleOutput: true,//禁用console方法
        // domainLock: ["127.0.0.1"],
        numbersToExpressions: true,//数字转换
        selfDefending: true,
        transformObjectKeys: true,
    });
    console.log(obfuscationResult.getObfuscatedCode());
})
