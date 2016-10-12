package action;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

import service.LogsService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import bean.Logs;

public class LogsAction extends ActionSupport {

	private LogsService logsService;// 业务层对象
	private Logs logs;// 待操作的对象
	private String keyword;// 界面层需要查询的属性：关键字
	private String logsStartDate;
	private int firstPage;// 显示的第一页
	private int lastPage;// 显示的最后一页
	private int currentPage;// 显示的当前页
	private int totalPage;// 总页数
	private int totalRecord;// 总记录数
	private final int RECORD_SIZE = 10;// 每页记录数
	private final int PAGE_SIZE = 10;// 每组的页数

	public String doAdd() throws Exception {
		logs.setLogsDate(logsStartDate);
		System.out.println("doAdd要添加的信息:" + logs);
		Logs db_logs = logsService.Add(logs);
		System.out.println("doAdd添加后的信息:" + db_logs);
		this.logs = db_logs;
		if (db_logs != null) {
			ActionContext.getContext().put("logs", logs);
			return "logsinfo_view";
		} else {
			ActionContext.getContext().put("errorMsg", logsService.getMsg());
			return "systemerror_view";
		}
	}

	/* 删除信息 */
	public String doDelete() throws Exception {
		Logs db_logs = logsService.View(logs.getLogsId());
		System.out.println("daDelete要删除的信息:" + db_logs);
		if (logsService.Delete(db_logs)) {
			return (doFind());
		} else {
			ActionContext.getContext().put("errorMsg", logsService.getMsg());
			return "systemerror_view";
		}
	}

	/* 查看信息 */
	public String doView() throws Exception {
		Logs db_logs = logsService.View(logs.getLogsId());
		this.logs = db_logs;
		if (db_logs != null) {
			ActionContext.getContext().put("logs", logs);
			return "logsinfo_view";
		} else {
			ActionContext.getContext().put("errorMsg", logsService.getMsg());
			return "systemerror_view";
		}
	}

	/* 信息列表 */
	public String doFind() throws Exception {
		if (totalRecord == 0)
			totalRecord = 1;
		if (totalPage == 0)
			totalPage = 1;
		if (firstPage == 0)
			firstPage = 1;
		if (currentPage == 0)
			currentPage = 1;
		if (lastPage == 0)
			lastPage = 1;
		if (keyword == null)
			keyword = "";

		totalRecord = logsService.GetCount(keyword);
		System.out.println("59594646" + totalRecord);
		totalPage = totalRecord / this.RECORD_SIZE + 1;
		if ((totalRecord % this.RECORD_SIZE == 0) && (totalRecord > this.RECORD_SIZE)) {
			totalPage--;
		}
		if (totalPage < PAGE_SIZE) {
			firstPage = 1;
			lastPage = totalPage;
		} else {
			firstPage = (currentPage / PAGE_SIZE) * PAGE_SIZE + 1;
			lastPage = firstPage + PAGE_SIZE - 1;
			if (lastPage > totalPage) {
				lastPage = totalPage;
			}
		}
		if (currentPage > totalPage) {
			System.out.println("currentPage>totalPage");
		} else {
			System.out.println("当前页码：" + currentPage + "页码列表：");
			for (int i = firstPage; i <= lastPage; i++) {
				System.out.print(i);
			}
		}
		int fromIndex = (currentPage - 1) * this.RECORD_SIZE; // 选择从第几条开始
		int toIndex = Math.min(fromIndex + this.RECORD_SIZE, totalRecord);// 调用Math.min函数取目的数

		System.out.println("当前页码：totalPage" + totalPage);
		System.out.println("当前页码：totalRecord" + totalRecord);
		System.out.println("当前页码：currentPage" + currentPage);
		System.out.println("当前页码：fromIndex" + fromIndex);
		System.out.println("当前页码：toIndex" + toIndex);
		System.out.println("当前页码：firstPage" + firstPage);
		System.out.println("当前页码：lastPage" + lastPage);

		List<?> list = logsService.Find(keyword, fromIndex, toIndex - fromIndex);// 可优化

		ActionContext ctx = ActionContext.getContext();
		ctx.put("list", list);
		ctx.put("totalRecord", totalRecord);
		ctx.put("totalPage", totalPage);
		ctx.put("firstPage", firstPage);
		ctx.put("currentPage", currentPage);
		ctx.put("lastPage", lastPage);
		ctx.put("PAGE_SIZE", PAGE_SIZE);
		ctx.put("keyword", keyword);
		return "logslist_view";
	}

	/* 修改Logs信息 */
	public String doEdit() throws Exception {
		System.out.println("doEdit要修改信息的ID：" + logs.getLogsId());
		Logs db_logs = logsService.View(logs.getLogsId());
		this.logs = db_logs;
		System.out.println("doEdit要修改信息：" + logs);
		if (db_logs != null) {
			ActionContext.getContext().put("logs", logs);
			return "logsedit_view";
		} else {
			ActionContext.getContext().put("errorMsg", logsService.getMsg());
			return "systemerror_view";
		}
	}

	/* 更新修改Logs信息 */
	public String doUpdate() throws Exception {
		logs.setLogsDate(logsStartDate);
		System.out.println("doUpdate要修改的信息:" + logs);
		Logs db_logs = logsService.Update(logs);
		this.logs = db_logs;
		if (db_logs != null) {
			ActionContext.getContext().put("logs", logs);
			System.out.println("doUpdate修改后信息:" + logs);
			return "logsinfo_view";
		} else {
			ActionContext.getContext().put("errorMsg", logsService.getMsg());
			return "systemerror_view";
		}
	}

	public LogsService getLogsService() {
		return logsService;
	}

	public void setLogsService(LogsService logsService) {
		this.logsService = logsService;
	}

	public Logs getLogs() {
		return logs;
	}

	public void setLogs(Logs logs) {
		this.logs = logs;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getLogsStartDate() {
		return logsStartDate;
	}

	public void setLogsStartDate(String logsStartDate) {
		this.logsStartDate = logsStartDate;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getRECORD_SIZE() {
		return RECORD_SIZE;
	}

	public int getPAGE_SIZE() {
		return PAGE_SIZE;
	}
}