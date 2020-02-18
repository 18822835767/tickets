package tickets;

public class TestTickets {
    public static void main(String[] args) {
        Tic t1 = new Tic();
        Tic t2 = new Tic();
        t1.setName("小明");
        t2.setName("小红");
        t1.start();
        t2.start();
    }
}

class Tic extends Thread{
    private static int tickets = 20000;
    private int num = 0;
    private static String str = "锁定对象";

    public void run(){
        while(true){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (str){
                if(num == 0){//只能买票
                    tickets--;
                    num++;
                }else if(tickets==0){
                    tickets++;//只能退票
                    num--;
                }else{//退票或买票
                    if(Math.random() > 0.3){
                        tickets--;
                        num++;
                    }else{
                        tickets++;//只能退票
                        num--;
                    }
                }
                System.out.println("商家持有票数：" + tickets);
                System.out.println("客户"+ Thread.currentThread().getName() + "持有票数：" + num);

            }
        }
    }
}