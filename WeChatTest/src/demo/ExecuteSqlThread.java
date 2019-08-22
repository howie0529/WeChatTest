package demo;


/**
 * 自定义线程启动执行下发sql语句
 * 
 * @author Administrator
 *
 */
public class ExecuteSqlThread  extends Thread{

	@Override
    public void run()  {
		try {
			System.out.println("=========================ExecuteSqlThread  start==============================");
			Thread bus = null;
			while (true) {
				bus = new Thread() {
					public void run() {
						//开始下载
						while(true) {
//							System.out.println("hahahahah");
						}
					}
				};
				// 任务线程启动
				bus.start();
				
				System.out.println("主线程(main)id=" + Thread.currentThread().getId() + " :任务线程id=" + bus.getId() + "开始执行");
				
				long thdId = bus.getId();
				// 启动任务执行时间监控器, 任务线程执行的最大时间
				String execute_thread_times = "";
				
				if(execute_thread_times == null || "".equals(execute_thread_times.trim()) ){
//					System.out.println("无法从数据库获取参数execute_thread_times.取默认值=600000");
					execute_thread_times= "10000";
				}
				
				long _execute_thread_times = Integer.parseInt(execute_thread_times);
				
				long  t = 0 ;
				// 等待业务线程运行结束				
				while (bus != null && bus.isAlive()) {
					//业务线程运行超过最大时限, kill该业务线程,结束监控
					if ( t > _execute_thread_times ) {
						try {
							bus.stop();
						} catch(Exception e){}
						System.out.println("任务线程id=" + thdId + "还在运行的任务,超最大运行时间"+execute_thread_times+"主线程(main)id=" + Thread.currentThread().getId() +"强行终止!");
						break ;
					}	
					//线程睡眠10秒
					Thread.sleep(10000L);
					t = t +  10000L ;
					//每2分钟打印一条正在运行中信息
					if (( t % 120000L) == 0 ) {
						System.out.println("任务线程id=" + thdId + "正在运行中, 已运行时间:" + t);
					}
				}

				//任务执行时间间隔 
				String run_execute_times = "";
				if(run_execute_times == null || "".equals(run_execute_times.trim()) ){
//					System.out.println("无法从数据库获取参数run_execute_times,.取默认值=60000");
					run_execute_times = "2000";
				}
				
				System.out.println("主线程(main)id=" + Thread.currentThread().getId() + "休眠"+run_execute_times+"秒!");
				Thread.sleep(Integer.parseInt(run_execute_times));
				
			}
		} catch (Exception e) {
			System.out.println("ExecuteSqlThread异常退出");
			e.printStackTrace();
		}
	}

}
