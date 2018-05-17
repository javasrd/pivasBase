<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
table tbody tr.odd {
    background-color: #F7F7F7;
}

table tbody tr.even {
    background-color: white;
}

table tbody tr.group_row{

	background-color: #E6EEF5;
}

#scheduleWeek td {
    border: 1px solid rgb(203, 221, 240);
}

#scheduleWeek .header td
{
    font-size: 15px;
    font-weight:bold;
    vertical-align: middle;
    min-width:60px;
    max-height:80px;
    height: 40px;
    text-align:center;
    background-color: #E6EEF5;
}


#scheduleWeek td.ownRestTimeBorder {
    border-left: 3px solid #3c763d;
}

#scheduleWeek .customizecell {
    width: 100px;
}


#scheduleWeek td.nametd 
{
    font-size: 14px;
    vertical-align: middle;
    width:80px;
    max-height:80px;
}

#scheduleWeek .postCla{
	height:30px;
}

#scheduleWeek .paiban_item {
    max-width: 200px;
    width:150px;
    vertical-align: top;
    height:40px;
}

#scheduleWeek input.hisinput
{
    border: 0px;
    padding: 0px;
    width: 95%;
	height: 30px;
	margin:5px 0 5px 0;
	line-height: 30px;
	text-align: center;
    background-color: #FFFFFF;
    cursor: pointer;
    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
}
#scheduleWeek input.hisinput[disabled]
{
	background:#ffffff!important;
}

#scheduleWeek .hasborder {
    border: 1px dashed #cccccc;
}
#scheduleWeek .schedualitem {
    border-width: 1;
    padding: 5px 0;
    position: relative;
    min-height:35px;
}

#scheduleWeek .hisschedualitem {
    text-align: center;
    max-width: 100px;
}

#scheduleWeek .hisschedualitemclose {
    position: absolute;
    /* z-index: -999; */
    top: 5px;
    right: 0px;
}

#scheduleWeek table .paibai_xiao_lists
{
    /*margin: 2px 2px 2px 2px;*/
    border-spacing: 1px;
    position: relative;
}
/* #scheduleWeek.schedule_print_content table .paibai_xiao_lists {
	margin-top: 0;
    margin-bottom:0;
} */

#scheduleWeek table .paibai_xiao_lists table
{    
    margin:auto
}

#scheduleWeek table .paibai_xiao_lists table tr
{
    height: 24px;
    padding-top:3px;
	/*排班行 横排显示*/ 
    float:left;
}
#scheduleWeek.schedule_print_content table .paibai_xiao_lists table tr {
	height: 32px;
    padding-top:0;
}

#scheduleWeek table .paibai_xiao_lists table tr:hover button
{
    display:block;
}

#scheduleWeek table .paibai_xiao_lists table td
{
    font-size: 14px;
    min-width: 40px;
    vertical-align:middle;    
    text-align:center;
}

#scheduleWeek.schedule_print_content table .paibai_xiao_lists table td
{
    font-size: 13px;
}

#scheduleWeek table .paibai_xiao_lists table td.deletebutton
{
    min-width:24px; 
    border-spacing: 0px;   
    border: 0px;
}

#scheduleWeek table td.tdselected
{
    background: #FECA40!important;
	color:black!important;
}

#scheduleWeek table td.paiban_item.ui-selected,
#scheduleWeek table td.paiban_item.ui-selecting { background: #FECA40!important; color:black!important; }

#scheduleWeek table td.tdselected {
    /* border: 2px solid #ABA0A0; */
    background: #FECA40!important;
    color: black!important;
}

.lastweek_plantype_row
{
	height:30px;
	background-color: rgb(224,255,255);
	text-align:center;
	
}
.lastweek_plantype_row .nametd {
	background-color:White;
}

.lastweek_plantype_cell span {
	margin-right:8px;
}

/*灰显非激活排班*/
.paiban_item tr[data-isactive="False"] .schedualitem,
.lastweek_plantype_cell span[data-isactive="False"]
{
	color:rgb(169, 169, 169);
	text-decoration: line-through;
}
</style>
<style media=print>
.Noprint {
display: none;
}
<!-- 用本样式在打印时隐藏非打印项目-->
</style>

<body>
<div id="scheduleWeek">

	<button class="btn Noprint"  title="打印" onclick="javascript:window.print();">打印</button>
	
	<div style="margin: 10px 0px 10px 0px;" id="timeText">
	<strong>${timeStr}</strong>
	</div>

	<table style="width: 100%;border-collapse: collapse">
		<thead class="header">
			<tr>
				<td align="center" colspan="1"></td>
				<td align="center" colspan="1" class="postCla hide"></td>
				<!--zj为了对齐，当现实岗位信息时显示这个单元格-->
				<!--表头日期，以后与星期信息合并-->

				<c:forEach var="date" items="${dateList}">
					<td align="center" class="dateHeader" >
							${date.name}
					</td>
				</c:forEach>

				<td class="ownRestTimeBorder" align="center" colspan="2">欠休
					(小时)</td>
			</tr>
			<tr>  
				<td align="center">姓名</td>

				<td align="center" class="customizecell postCla">岗位</td>
				<!--zj-->

				<td align="center" class="weekHeader">
					<div>星期一</div>
				</td>
				<td align="center" class="weekHeader">
					<div>星期二</div>
				</td>
				<td align="center" class="weekHeader">
					<div>星期三</div>
				</td>
				<td align="center" class="weekHeader">
					<div>星期四</div>
				</td>
				<td align="center" class="weekHeader">
					<div>星期五</div>
				</td>
				<td align="center" class="weekHeader">
					<div>星期六</div>
				</td>
				<td align="center" class="weekHeader">
					<div>星期日</div>
				</td>

				<td class="ownRestTimeBorder" align="center">本周</td>
				<td align="center">累计</td>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="scheduleList" items="${scheduleList}">
				<tr style="height: 30px; font-weight: bold" class="group_row">
					<td colspan="13" class="nametd" align="center">${fn:escapeXml(scheduleList.groupName)}</td>
				</tr>
				<c:forEach var="groupInfoList" items="${scheduleList.groupInfoList}"
					varStatus="status">

					<c:if test="${status.index%2==0}">
						<tr style="height: 30px" class="odd current_week_plantype_row">
					</c:if>
					<c:if test="${status.index%2!=0}">
						<tr style="height: 30px" class="even current_week_plantype_row">
					</c:if>
					<td class="nametd" align="center" title="">
						<span>${fn:escapeXml(groupInfoList.user_name)}</span>
					</td>

					<td class="postCla hide" align="center">
						<%--zj 显示岗位--%>
						${fn:escapeXml(groupInfoList.managerBean.postName)}
					</td>

					<c:forEach var="weekData"
						items="${groupInfoList.managerBean.weekBeanList}">

						<td class="paiban_item" align="center">
							
							<div class="paibai_xiao_lists">
							
								<c:forEach var="data" items="${weekData.weekDataBean}">

									<div data-isactive="True">
										<div class="schedualitem  hasborder"
											style="background-color:${data.workColour};">
											<div class="hisschedualitem">
											${fn:escapeXml(data.workName)}
											</div>
										</div>
									</div>

								</c:forEach>

							</div>
						</td>

					</c:forEach>

					<td class="nametd ownRestTime_currentweek ownRestTimeBorder"
						align="center" >
						<span>${groupInfoList.managerBean.week_owetime}</span>
					</td>
					<td class="nametd ownRestTime_total" align="center">
					<span>
					${groupInfoList.managerBean.total_owetime}
					</span>
					</td>
					</tr>

				</c:forEach>

			</c:forEach>

		</tbody>
	</table>
	
</div>
</body>
</html>