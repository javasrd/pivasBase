<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<style>

#hisschedule .header td
{
    font-size: 11px;
    font-weight:bold;
    vertical-align: middle;
    min-width:20px;
    max-height:80px;
    height: 40px;
    text-align:center;
    background-color: #E6EEF5;
}
#hisschedule td {
    border: 1px solid rgb(203, 221, 240);
}

#hisschedule td.nametd {
    font-size: 14px;
    vertical-align: middle;
    width: 80px;
    max-height: 80px;
}

#hisschedule .itemhost
{
    border: 0px dashed #008080;
    position: relative;
    top: 0;
    left: 0;
    height: 50px;
    margin: 0px;
}

#hisschedule div.item
{
    background-color: #C0C0C0;
    border: 1px solid #000000;
    position: absolute;
    display: inline-block;
    width: 80px;
    height: 48px;
    top: 1px;
    left: 2px;
    text-align: center;
    vertical-align: middle;
    overflow: hidden;
    line-height: 40px;
}

#hisschedule .item span.content
{
    font-size: 12px;
    background-color: transparent;
    overflow: hidden;
    border-width: 0px;
}

#hisschedule thead .dateHeader
{
    width:3.5%;
} 

#hisschedule thead .showHeader
{
    width:8%;
}
#hisschedule tbody .itemcontainer
{
	width:84%;
}
</style>

<body>
<div id="hisschedule" data-baseday="${dayDate}" data-unit="day">

		<table style="width: 100%">
			<thead class="header">
				<tr>
					<td align="center" class="showHeader">姓名</td>

					<td align="center" class="showHeader">岗位</td>

					<td style="text-align: center;" class="dateHeader">
						<div>0:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>1:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>2:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>3:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>4:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>5:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>6:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>7:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>8:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>9:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>10:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>11:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>12:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>13:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>14:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>15:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>16:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>17:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>18:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>19:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>20:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>21:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>22:00</div>
					</td>
					<td style="text-align: center;" class="dateHeader">
						<div>23:00</div>
					</td>
				</tr>
			</thead>
			<tbody><%-- class="ui-selectable ui-selectable-disabled ui-state-disabled" aria-disabled="true" --%>
				<c:forEach var="dayList" items="${dayList}">
				
					<tr style="height: 30px; font-weight: bold" class="group_row">
						<td colspan="26" class="nametd" align="center">${fn:escapeXml(dayList.groupName)}</td>
					</tr>
					
					<c:forEach var="groupInfoList" items="${dayList.groupInfoList}"
					varStatus="status">
						
					<c:if test="${status.index%2==0}">
						<tr style="height: 30px" class="odd current_week_plantype_row">
					</c:if>
					<c:if test="${status.index%2!=0}">
						<tr style="height: 30px" class="even current_week_plantype_row">
					</c:if>
						
							<td align="center" title="" class="nametd "><span>${fn:escapeXml(groupInfoList.user_name)}</span></td>
							<td align="center" class="nametd "><span>${fn:escapeXml(groupInfoList.managerBean.postName)}</span></td>

							<td colspan="24" class="itemcontainer">
								<div class="itemhost">
								
								<c:forEach var="dayWork" items="${groupInfoList.managerBean.dayWorkList}">
								
								<div class="item "
									style="text-align: center; left: ${dayWork.left}%; width: ${dayWork.width}%; background-color: ${dayWork.workColour};">
									<span class="content" >${fn:escapeXml(dayWork.workName)}</span>
								</div>
								
								</c:forEach>
								
								</div>
							</td>

						</tr>
					
					</c:forEach>
				
				</c:forEach>

			</tbody>
		</table>
	</div>


</body>
</html>