<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
#scheduleWeek td {
    border: 1px solid rgb(203, 221, 240);
}

#scheduleWeek .header td
{
    font-size: 12px;
    font-weight:bold;
    vertical-align: middle;
    min-width:60px;
    max-height:80px;
    height: 40px;
    text-align:center;
    background-color: #648ceb;
	color:#fff;
}
#scheduleWeek .header td a{color:#fff}

#scheduleWeek td.ownRestTimeBorder {
    border-left: 1px solid #3c763d;
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
    min-height: 35px;
}

#scheduleWeek .hisschedualitem {
    text-align: center;
    max-width: 150px;
}

#scheduleWeek .hisschedualitemclose {
    position: absolute;
    /* z-index: -999; */
    top: 5px;
    right: 0px;
}

#scheduleWeek table .paibai_xiao_lists
{
    margin: 2px 2px 2px 2px;
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
<body>
	<table class="table-bordered" style="width: 100%">
		<thead class="header">
			<tr>
				<td align="center" colspan="1" rowspan="2">姓名</td>
				<td align="center" colspan="1" class="postCla hide"></td>
				<!--zj为了对齐，当现实岗位信息时显示这个单元格-->
				<!--表头日期，以后与星期信息合并-->

				<c:forEach var="date" items="${dateList}">
					<td align="center" class="dateHeader" data-value="${date.value}">
						<div>
							<a class="schedualbyday" href="#" onclick="loadDay('${date.value}')">${date.name}</a>
						</div>
					</td>
				</c:forEach>

				<!-- <td align="center" class="dateHeader" data-value="2016-2-15">
                                <div><a class="schedualbyday" href="#">15</a></div>
                        </td>
                        <td align="center" class="dateHeader" data-value="2016-2-16">
                                <div><a class="schedualbyday" href="#">16</a></div>
                        </td>
                         -->

				<td class="ownRestTimeBorder" align="center" colspan="2">欠休
					(小时)</td>
			</tr>
			<tr>

				<td align="center" class="customizecell postCla hide">岗位</td>
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
			<!-- class="ui-selectable ui-selectable-disabled ui-state-disabled" -->

			<c:forEach var="scheduleList" items="${scheduleList}">
				<tr style="height:30px;font-weight:bold;color:#333" class="group_row">
					<td colspan="13" class="nametd" align="left" style="text-indent:25px;">${fn:escapeXml(scheduleList.groupName)}</td>
				</tr>
				<c:forEach var="groupInfoList" items="${scheduleList.groupInfoList}"
					varStatus="status">

					<tr class="lastweek_plantype_row hide">

						<td class="nametd" align="center" title="" rowspan="2"><span>${fn:escapeXml(groupInfoList.user_name)}</span>
						</td>
						<td align="center" colspan="1" class="postCla hide"><%-- 岗位 --%>
							<span>${fn:escapeXml(groupInfoList.lastManagerBean.postName)}</span>
						</td>
						 <c:forEach var="lastWeek" items="${groupInfoList.lastManagerBean.lastWeekList}">  
                         <td class="lastweek_plantype_cell ">
                             <div>
                             <span>${fn:escapeXml(lastWeek.value)}</span>
                             </div>
                         </td>
                         </c:forEach>

						<td align="center"><span>${groupInfoList.lastManagerBean.week_owetime}</span>
						</td>
						<td align="center"><span>${groupInfoList.lastManagerBean.total_owetime}</span>
						</td>
					</tr>

					<c:if test="${status.index%2==0}">
						<tr style="height: 30px" class="odd current_week_plantype_row"
							data-personid="${groupInfoList.user_id}"
							data-groupname="${fn:escapeXml(groupInfoList.groupName)}"
							data-groupid="${groupInfoList.groupId}">
					</c:if>
					<c:if test="${status.index%2!=0}">
						<tr style="height: 30px" class="even current_week_plantype_row"
							data-personid="${groupInfoList.user_id}"
							data-groupname="${fn:escapeXml(groupInfoList.groupName)}"
							data-groupid="${groupInfoList.groupId}">
					</c:if>
					<td class="nametd" data-name="${fn:escapeXml(groupInfoList.user_name)}" align="center" title="" style="color:#314086;font-weight: bold">
						<span>${fn:escapeXml(groupInfoList.user_name)}</span>
					</td>
					<td class="postCla hide" align="center">
						<%--zj 显示岗位--%>
						<select name="postSelect" class="form-control" disabled>
							<option value=""></option>
							<c:forEach var="post" items="${postList}">
								<c:if test="${groupInfoList.managerBean.postId!=post.postId}">
									<option value="${post.postId}">${fn:escapeXml(post.postName)}</option>
								</c:if>
								<c:if test="${groupInfoList.managerBean.postId==post.postId}">
									<option value="${post.postId}" selected>${fn:escapeXml(post.postName)}</option>
								</c:if>
							</c:forEach>
					</select>
					</td>

					<c:forEach var="weekData"
						items="${groupInfoList.managerBean.weekBeanList}">

						<td class="paiban_item" align="center"
							data-personid="${weekData.user_id}"
							data-daystring="${weekData.workDate}">
							<div class="paibai_xiao_lists" data-daytime="${weekData.workTotalTime}" data-olddaytime="${weekData.workTotalTime}">
								<%-- <button type="button" class="close hisschedualitemadd planitemactionicon hide" data-actiontype="add">
								<span class="icon16 brocco-icon-plus "></span>
								</button> --%>      
								<c:forEach var="data" items="${weekData.weekDataBean}">

									<div data-workid="${data.workId}" data-isactive="True" style="margin:2px 0">
										<div class="schedualitem  hasborder"
											data-worktype="${data.workType}"
											data-worktime="${data.totalTime}"
											data-workid="${data.workId}"
											data-personid="${weekData.user_id}"
											data-colour="${data.workColour}"
											style="background-color:${data.workColour};border-radius:3px">
											<div class="hisschedualitem">${fn:escapeXml(data.workName)}</div>
											<button type="button" class="close hisschedualitemclose hide">
												<span>x</span>
											</button>
										</div>
									</div>

								</c:forEach>

							</div>
						</td>

					</c:forEach>


					<td class="nametd ownRestTime_currentweek ownRestTimeBorder"
						align="center" data-totaltime="0" >
						<span>${groupInfoList.managerBean.week_owetime}</span>
					</td>
					<td class="nametd ownRestTime_total" align="center">
					<span>
					<input type="text" class="hisinput text" valueType="number"
							data-change="0"
							data-lastvalue="${groupInfoList.lastManagerBean.total_owetime}"
							data-oldvalue="${groupInfoList.managerBean.total_owetime}"
							value="${groupInfoList.managerBean.total_owetime}" disabled
							onclick="checkOwetime(this)" onblur="changeOwetime(this)">
					</span>
					</td>
					</tr>
					<tr></tr>
				</c:forEach>

			</c:forEach>

		</tbody>
	</table>
	
	
</body>
</html>