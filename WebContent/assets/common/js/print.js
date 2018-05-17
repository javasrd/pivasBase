function myPrint(obj, style) {
	var objs = document.getElementById(obj);
	var style = style || 'toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no';
	var newWindow = window.open('/ami/assets/common/js/print.jsp', '_blank', style);
	setTimeout(function() {
		$(newWindow.document.body).html('<table width="99.4%" cellpadding="0" cellspacing="0" style="margin-left:5px;margin-right:5px;"><tr><td style="padding:2px;">'+$(objs).html()+'</td></tr></table>');
		newWindow.print();
		newWindow.close();
	}, 100);

}
/*调用样
var param = {};
param.dataTime = '2015-3-17 15:52:35';
param.custName = 'li che feng';
param.meterNo = '54 77888999555555';
param.custNo = 'ML';
param.totalAmount = '100.00';
param.taxAmount = '8.00';
param.netAmount = '92.00';
param.energy = '100.00';
param.creditToken = '1751 5805 1674 9554 6046';
param.sgc = '901789';
param.krn = '1';
param.tokenID = '1';
doPrint(param,9000);
*/
function doPrint(obj,style) {
	var newWindow = window.open('/ami/assets/common/js/print.jsp', '_blank', style);
	var html = '<table id="printTable" style="overflow:hidden; word-break:break-all; word-wrap:break-word;" cellpadding="0" cellspacing="0"\
		style="margin-left:5px;margin-right:5px;">\
		<tr>\
		<td colspan="2" style="text-align:center;">Credit Voucher</td>\
		</tr>\
		<tr> <td style="width:120px;"></td> <td style="width:180px;"></td></tr>';
		if(obj.dataTime != '')
		{
			html = html + '<tr> <td>Date:</td><td>' + obj.dataTime + '</td></tr>';
		}
		
		if(obj.custName != '')
		{
			html = html + '<tr> <td>Customer Name:</td><td style="text-align:right;"><font style="color: red;font-weight: bolder;">' + obj.custName + '</font></td></tr>';
		}
		
		if(obj.meterNo != '')
		{
			html = html + '<tr> <td>Meter No:</td><td>' + obj.meterNo + '</td></tr>';
		}
		
		if(obj.custNo != '')
		{
			html = html + '<tr> <td>Customer No:</td><td>' + obj.custNo + '</td></tr>';
		}
		
		if(obj.totalAmount != '')
		{
			html = html + '<tr> <td>Total Amount:</td><td style="text-align:right;">Kz ' + obj.totalAmount + '</td></tr>';
		}
		
		if(obj.taxAmount != '')
		{
			html = html + '<tr> <td>Tax Amount:</td><td style="text-align:right;">Kz ' + obj.taxAmount + '</td></tr>';
		}
		
		if(obj.netAmount != '')
		{
			html = html + '<tr> <td><hr size="1" width="100%" color="black" noshade="noshade" /></td><td><hr size="1" width="100%" color="black" noshade="noshade" /></td></tr>\
			<tr> <td>Net Amount:</td><td style="text-align:right;font-weight:bold;">Kz ' + obj.netAmount + '</td></tr>';
		}
		
		if(obj.creditToken != '')
		{
			html = html + '<tr> <td>Credit Token:</td><td>' + obj.creditToken + '</td></tr>';
		}
		
		if(obj.sgc != '')
		{
			html = html + '<tr> <td>SGC:</td><td>' + obj.sgc + '</td></tr>';
		}
		
		if(obj.krn != '')
		{
			html = html + '<tr> <td>KRN:</td><td>' + obj.krn + '</td></tr>';
		}
		
		if(obj.tokenID != '')
		{
			html = html + '<tr> <td>TI:</td><td><font style="color: red;font-weight: bolder;">' + obj.tokenID + '</font></td></tr>';
		}
	html = html + '</table>';
	setTimeout(function() {
		$(newWindow.document.body).html(html);
		newWindow.print();
		newWindow.close();
	},1000);
}
function doPrintAfter(objS,style) {
	var newWindow = window.open('/ami/assets/common/js/print.jsp', '_blank', style);
	var html = '<table id="printTable" style="overflow:hidden; word-break:break-all; word-wrap:break-word;" cellpadding="0" cellspacing="0"\
		style="margin-left:5px;margin-right:5px;">';
	for(var k=0;k<objS.length;k++){
		var obj = objS[k];
		html = html + '<tr><td> </td></tr>' + 
		'<tr>\
		<td colspan="3" style="text-align:center;">Credit Voucher</td>\
		</tr>\
		<tr> <td style="width:120px;"></td> <td style="width:50px;"></td> <td style="width:180px;"></td></tr>';
		if(obj.ticketTime != '')
		{
			html = html + '<tr> <td>Date:</td><td colspan="2" style="text-align:right;">' + obj.ticketTime + '</td></tr>';
		}
		
		if(obj.custName != '')
		{
			html = html + '<tr> <td colspan="2" >Customer Name:</td><td style="text-align:right;">' + obj.custName + '</td></tr>';
		}
		
		if(obj.custCode != '')
		{
			html = html + '<tr> <td colspan="2" >Customer No:</td><td style="text-align:right;">' + obj.custCode + '</td></tr>';
		}
		
		html = html + '<tr> <td colspan="2" >----------------------</td><td style="text-align:right;">------------------</td></tr>';
		html = html + '<tr> <td colspan="2" >Total Amount:</td><td style="text-align:right;"><font style="color: red;font-weight: bolder;">' + obj.totalAmount + '</font></td></tr>';
		
		html = html + '<tr> <td colspan="2" >Tax Amount:</td><td style="text-align:right;"><font style="color: red;font-weight: bolder;">' + obj.taxAmount + '</font></td></tr>';
		
		if(obj.netAmount != '')
		{
			html = html + '<tr> <td colspan="2" >Net Amount:</td><td style="text-align:right;"><font style="color: red;font-weight: bolder;">' + obj.netAmount + '</font></td></tr>';
		}
		html = html + '<tr> <td colspan="2" >----------------------</td><td style="text-align:right;">------------------</td></tr>';
		html = html + '<tr><td> </td></tr><tr><td> </td></tr><tr><td> </td></tr><tr><td> </td></tr>';
		
	}
	html = html + '</table>';
	setTimeout(function() {
		$(newWindow.document.body).html(html);
		newWindow.print();
		newWindow.close();
	},1000);
}
function doPrintPre(objS,style) {
	var newWindow = window.open('/ami/assets/common/js/print.jsp', '_blank', style);
	var html = '<table id="printTable" style="overflow:hidden; word-break:break-all; word-wrap:break-word;" cellpadding="0" cellspacing="0"\
		style="margin-left:5px;margin-right:5px;">';
	for(var k=0;k<objS.length;k++){
		var obj = objS[k];
		html = html + '<tr><td> </td></tr>' + 
		'<tr>\
		<td colspan="3" style="text-align:center;">Credit Voucher</td>\
		</tr>\
		<tr> <td style="width:120px;"></td> <td style="width:50px;"></td> <td style="width:180px;"></td></tr>';
		if(obj.ticketTime != '')
		{
			html = html + '<tr> <td>Date:</td><td colspan="2" style="text-align:right;">' + obj.ticketTime + '</td></tr>';
		}
		

		if(obj.custName != '')
		{
			html = html + '<tr> <td colspan="2" >Customer Name:</td><td style="text-align:right;">' + obj.custName + '</td></tr>';
		}
		
		if(obj.custCode != '')
		{
			html = html + '<tr> <td colspan="2" >Customer No:</td><td style="text-align:right;">' + obj.custCode + '</td></tr>';
		}
		
		if(obj.deviceNum != '')
		{
			html = html + '<tr> <td colspan="2" >Meter No:</td><td style="text-align:right;">' + obj.deviceNum + '</td></tr>';
		}

		html = html + '<tr> <td colspan="2" >----------------------</td><td style="text-align:right;">------------------</td></tr>';

		if(obj.totalAmount != '')
		{
			html = html + '<tr> <td colspan="2">Total Amount:</td><td style="text-align:right;">' + obj.totalAmount + '</td></tr>';
		}
		
		html = html + '<tr> <td colspan="2" >Tax Amount:</td><td style="text-align:right;"><font style="color: red;font-weight: bolder;">' + obj.taxAmount + '</font></td></tr>';
		
		if(obj.netAmount != '')
		{
			html = html + '<tr> <td colspan="2" >Net Amount:</td><td style="text-align:right;"><font style="color: red;font-weight: bolder;">' + obj.netAmount + '</font></td></tr>';

		}
		html = html + '<tr> <td colspan="2" >----------------------</td><td style="text-align:right;">------------------</td></tr>';
		
		if(obj.energy != '')
		{
			html = html + '<tr> <td colspan="2" >Energy:</td><td style="text-align:right;">' + obj.energy + 'KWH</td></tr>';
		}
		html = html + '<tr> <td colspan="2" >**********************</td><td style="text-align:right;">******************</td></tr>';
		if(obj.creditToken != '')
		{
			html = html + '<tr> <td colspan="2">Credit Token:</td><td style="text-align:right;">' + obj.creditToken + '</td></tr>';
		}
		html = html + '<tr> <td colspan="2" >**********************</td><td style="text-align:right;">******************</td></tr>';
		
		html = html + '<tr> <td colspan="2" >SGC:</td><td style="text-align:right;">' + obj.sgc + '</td></tr>';

		html = html + '<tr> <td colspan="2" >KRN:</td><td style="text-align:right;">' + obj.krn + '</td></tr>';
		
		html = html + '<tr> <td colspan="2" >TI:</td><td style="text-align:right;"><font style="color: red;font-weight: bolder;">' + obj.tokenID + '</font></td></tr>';
		
		html = html + '<tr><td> </td></tr><tr><td> </td></tr><tr><td> </td></tr><tr><td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>';
		
	}
	html = html + '</table>';
	setTimeout(function() {
		$(newWindow.document.body).html(html);
		newWindow.print();
		newWindow.close();
	},1000);
}

function doPrintAfterRefund(objS,style) {
	var newWindow = window.open('/ami/assets/common/js/print.jsp', '_blank', style);
	var html = '<table id="printTable" style="overflow:hidden; word-break:break-all; word-wrap:break-word;" cellpadding="0" cellspacing="0"\
		style="margin-left:5px;margin-right:5px;">';
	for(var k=0;k<objS.length;k++){
		var obj = objS[k];
		html = html + '<tr><td> </td></tr>' + 
		'<tr>\
		<td colspan="3" style="text-align:center;">Credit Voucher</td>\
		</tr>\
		<tr> <td style="width:120px;"></td> <td style="width:50px;"></td> <td style="width:180px;"></td></tr>';
		if(obj.ticketTime != '')
		{
			html = html + '<tr> <td>Date:</td><td colspan="2" style="text-align:right;">' + obj.ticketTime + '</td></tr>';
		}
		
		if(obj.custName != '')
		{
			html = html + '<tr> <td colspan="2" >Customer Name:</td><td style="text-align:right;">' + obj.custName + '</td></tr>';
		}
		
		if(obj.custCode != '')
		{
			html = html + '<tr> <td colspan="2" >Customer No:</td><td style="text-align:right;">' + obj.custCode + '</td></tr>';
		}
		html = html + '<tr> <td colspan="2" >----------------------</td><td style="text-align:right;">------------------</td></tr>';
		html = html + '<tr> <td colspan="2" ><font style="color: red;font-weight: bolder;">Tax Amount:</td><td style="text-align:right;">' + obj.taxAmount + '</font></td></tr>';
		
		if(obj.netAmount != '')
		{
			html = html + '<tr> <td><hr size="1" width="100%" color="black" noshade="noshade" /></td><td colspan="2" ><hr size="1" width="100%" color="black" noshade="noshade" /></td></tr>\
			<tr> <td colspan="2" >Refund Amount:</td><td style="text-align:right;"><font style="color: red;font-weight: bolder;">' + obj.netAmount + '</font></td></tr>';
		}
		html = html + '<tr> <td colspan="2" >----------------------</td><td style="text-align:right;">------------------</td></tr>';
		html = html + '<tr><td> </td></tr><tr><td> </td></tr><tr><td> </td></tr><tr><td> </td></tr>';
		
	}
	html = html + '</table>';
	setTimeout(function() {
		$(newWindow.document.body).html(html);
		newWindow.print();
		newWindow.close();
	},1000);
}

function doPrintAfterRefundM(objS,style) {
	var newWindow = window.open('/ami/assets/common/js/print.jsp', '_blank', style);
	var html = '<table id="printTable" style="overflow:hidden; word-break:break-all; word-wrap:break-word;" cellpadding="0" cellspacing="0"\
		style="margin-left:5px;margin-right:5px;">';
	for(var k=0;k<objS.length;k++){
		var obj = objS[k];
		html = html + '<tr><td> </td></tr>' + 
		'<tr>\
		<td colspan="3" style="text-align:center;">Credit Voucher</td>\
		</tr>\
		<tr> <td style="width:120px;"></td> <td style="width:50px;"></td> <td style="width:180px;"></td></tr>';
		if(obj.ticketTime != '')
		{
			html = html + '<tr> <td>Date:</td><td colspan="2" style="text-align:right;">' + obj.ticketTime + '</td></tr>';
		}
		
		if(obj.custName != '')
		{
			html = html + '<tr> <td colspan="2" >Customer Name:</td><td style="text-align:right;">' + obj.custName + '</td></tr>';
		}
		
		if(obj.custCode != '')
		{
			html = html + '<tr> <td colspan="2" >Customer No:</td><td style="text-align:right;">' + obj.custCode + '</td></tr>';
		}
		html = html + '<tr> <td colspan="2" >----------------------</td><td style="text-align:right;">------------------</td></tr>';
		html = html + '<tr> <td colspan="2" >Tax Amount:</td><td style="text-align:right;"><font style="color: red;font-weight: bolder;">' + obj.taxAmount + '</font></td></tr>';
		
		if(obj.netAmount != '')
		{
			html = html + '<tr> <td><hr size="1" width="100%" color="black" noshade="noshade" /></td><td colspan="2" ><hr size="1" width="100%" color="black" noshade="noshade" /></td></tr>\
			<tr> <td colspan="2" >Refund Money:</td><td style="text-align:right;"><font style="color: red;font-weight: bolder;">' + obj.netAmount + '</font></td></tr>';
		}
		html = html + '<tr> <td colspan="2" >----------------------</td><td style="text-align:right;">------------------</td></tr>';
		html = html + '<tr><td> </td></tr><tr><td> </td></tr><tr><td> </td></tr><tr><td> </td></tr>';
		
	}
	html = html + '</table>';
	setTimeout(function() {
		$(newWindow.document.body).html(html);
		newWindow.print();
		newWindow.close();
	},1000);
}
function doPrintPreRefund(objS,style) {
	var newWindow = window.open('/ami/assets/common/js/print.jsp', '_blank', style);
	var html = '<table id="printTable" style="overflow:hidden; word-break:break-all; word-wrap:break-word;" cellpadding="0" cellspacing="0"\
		style="margin-left:5px;margin-right:5px;">';
	for(var k=0;k<objS.length;k++){
		var obj = objS[k];
		html = html + '<tr><td> </td></tr>' + 
		'<tr>\
		<td colspan="3" style="text-align:center;">Credit Voucher</td>\
		</tr>\
		<tr> <td style="width:120px;"></td> <td style="width:50px;"></td> <td style="width:180px;"></td></tr>';
		if(obj.ticketTime != '')
		{
			html = html + '<tr> <td>Date:</td><td colspan="2" style="text-align:right;">' + obj.ticketTime + '</td></tr>';
		}
		
		if(obj.custName != '')
		{
			html = html + '<tr> <td colspan="2" >Customer Name:</td><td style="text-align:right;">' + obj.custName + '</td></tr>';
		}
		
		if(obj.custCode != '')
		{
			html = html + '<tr> <td colspan="2" >Customer No:</td><td style="text-align:right;">' + obj.custCode + '</td></tr>';
		}
		
		if(obj.deviceNum != '')
		{
			html = html + '<tr> <td colspan="2" >Meter No:</td><td style="text-align:right;">' + obj.deviceNum + '</td></tr>';
		}

		html = html + '<tr> <td colspan="2" >TI:</td><td style="text-align:right;"><font style="color: red;font-weight: bolder;">' + obj.tokenID + '</font></td></tr>';

		html = html + '<tr> <td colspan="2" >SGC:</td><td style="text-align:right;">' + obj.sgc + '</td></tr>';

		html = html + '<tr> <td colspan="2" >KRN:</td><td style="text-align:right;">' + obj.krn + '</td></tr>';
		html = html + '<tr> <td colspan="2" >----------------------</td><td style="text-align:right;">------------------</td></tr>';
		html = html + '<tr> <td colspan="2" >Tax Amount:</td><td style="text-align:right;"><font style="color: red;font-weight: bolder;">' + obj.taxAmount + '</font></td></tr>';
		
		if(obj.netAmount != '')
		{
			html = html + '<tr> <td colspan="2" >Refund Amount:</td><td style="text-align:right;"><font style="color: red;font-weight: bolder;">' + -1*Number(obj.netAmount) + '</font></td></tr>';
		}
		html = html + '<tr> <td colspan="2" >----------------------</td><td style="text-align:right;">------------------</td></tr>';
		html = html + '<tr><td> </td></tr><tr><td> </td></tr><tr><td> </td></tr><tr><td> </td></tr>';
		
	}
	html = html + '</table>';
	setTimeout(function() {
		$(newWindow.document.body).html(html);
		newWindow.print();
		newWindow.close();
	},1000);
}