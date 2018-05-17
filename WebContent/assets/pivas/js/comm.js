var dicMapMapS = '{ "ageUnit": { "0": "天", "1": "月", "2": "岁" }, "medicRunStatus": { "0": "执行中", "1": "停止", "2": "撤销" }, "printStatus": { "0": "已打印", "1": "未打印" },"selfbuy": { "0": "非自备药", "1": "自备药" },"sex": { "0": "女", "1": "男" },"yesNo": { "0": "否", "1": "是" },"yzshzt": { "0": "未审核", "1": "审核通过", "2": "审核不通过" },"yzzt": { "0": "执行", "1": "停止", "2": "撤销" }, "yzlx": { "0": "长嘱", "1": "临嘱" }, "pqzt": { "0": "未打印", "1": "停止", "2": "撤销", "3": "未打印", "4": "已打印", "5": "已入仓", "6": "仓内完成","7":"出仓完成","8":"已签收","-1":"已退费" },"ydzxzt": { "0": "执行", "1": "停止", "2": "撤销", "3": "退费"} ,"ydrecord": { "0": "执行", "1": "停止", "2": "退费"} }';
var dicMapMap = JSON.parse(dicMapMapS);

function getDicValue(dicname, dickey) {
    var v = '';
    var dicOne = dicMapMap[dicname];
    if (dicOne) {
        v = dicOne[dickey];
    }
    if (v != undefined) {
        return v;
    }
    return "";
}
