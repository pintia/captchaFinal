// // import {JavaScriptObfuscator} from "node_modules/javascript-obfuscator/dist/index.browser.js";
// const { JavaScriptObfuscator } = require("javascript-obfuscator");
//
// function obfuscator(s){
//     // var JavaScriptObfuscator = require("./javascript-obfuscator");
//     var obfuscationResult = JavaScriptObfuscator.obfuscate(s);
//     // console.log(s);
//     //return obfuscationResult.getObfuscatedCode();
// }
// obfuscator("a");

var JavaScriptObfuscator = require('javascript-obfuscator');
const args = process.argv.slice(2)
var obfuscationResult = JavaScriptObfuscator.obfuscate( '', {
    controlFlowFlattening: true, //影响性能
    deadCodeInjection: true,//影响代码量和文件大小
    debugProtection: true,//影响开发者工具
    debugProtectionInterval: 4000,
    disableConsoleOutput: true,//禁用console方法
    // domainLock: ["127.0.0.1"],
    numbersToExpressions: true,//数字转换
    selfDefending: true,
    transformObjectKeys: true,
    inputFileName: "./",
});

console.log(obfuscationResult.getObfuscatedCode());

