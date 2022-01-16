package cf.thdisstudio.asdfdasfadsf;

public class th extends Thread{
    public th(String code){
        this.setName(code);
    }

    boolean isOff = false;

    @Override
    public void run() {
        try {
            Thread.sleep(300000);
            if(!isOff)
                Asdfdasfadsf.list.remove(this.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void interrupt() {
        isOff = true;
    }
}
