package tickets;
//Client进行操作后，Server就打印票数的变化
import java.io.Serializable;

class Tickets implements Serializable{
    private int count = 20000;//一开始商家卖的总票数
    private boolean mark = false;//标记客户是否已执行操作,以便商家打印剩余票信息

    public synchronized int buy(int num){//客户买票
        while(mark){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notifyAll();

        num++;
        count--;
        System.out.println(Thread.currentThread().getName() + "持有票数：" + num);
        mark = true;
        return num;
    }

    public synchronized int back(int num){//客户退票
        while(mark){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notifyAll();

        num--;
        count++;
        System.out.println(Thread.currentThread().getName() + "持有票数：" + num);
        mark = true;
        return num;
    }

    public synchronized void print(){
        while(!mark){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notifyAll();

        System.out.println(Thread.currentThread().getName() + "剩余票数：" + count);
        mark = false;
    }

    public int getcount(){return count;}
}

class Server implements Runnable {
    private Tickets tickets;

    public Server(Tickets tickets){ this.tickets = tickets; }

    public void run(){
        while(true){
            tickets.print();
        }
    }
}

class Client implements Runnable{
    private Tickets tickets;
    private int num = 0;

    public Client(Tickets tickets){ this.tickets = tickets; }

    public void run(){
        while(true){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(num == 0){//只能买票
                    num = tickets.buy(num);
                }else if(tickets.getcount()==0){
                    num = tickets.back(num);//只能退票
                }else{//退票或买票
                    if(Math.random() > 0.3){
                        num = tickets.buy(num);//买票
                    }else{
                        num = tickets.back(num);//只能退票
                    }
                }

            }
        }
}

public class TestSC {
    public static void main(String[] args) {
        Tickets tickets = new Tickets();
        Server s1 = new Server(tickets);
        Client c1 = new Client(tickets);
        Client c2 = new Client(tickets);
        Thread t1 = new Thread(s1);
        Thread t2 = new Thread(c1);
        Thread t3 = new Thread(c2);
        t1.setName("Server");
        t2.setName("小明");
        t3.setName("小红");
        t1.start();
        t2.start();
        t3.start();
    }
}
