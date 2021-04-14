package backEnd;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

public class Wallet {
    /**
     * The RandomAccessFile of the wallet file
     */  
    private RandomAccessFile file;

    /**
     * Creates a Wallet object
     *
     * A Wallet object interfaces with the wallet RandomAccessFile
     */
    public Wallet () throws Exception {
	this.file = new RandomAccessFile(new File("backEnd/wallet.txt"), "rw");
    }

    /**
     * Gets the wallet balance. 
     *
     * @return                   The content of the wallet file as an integer
     */
    public int getBalance() throws IOException {
	this.file.seek(0);
	return Integer.parseInt(this.file.readLine());
    }

    //Enhanced getbalance method with lock protection 
    public int getBalanceSafeWithdrawal() throws IOException {
       FileLock lock = file.getChannel().lock();
       this.file.seek(0);
       lock.release();
       return Integer.parseInt(this.file.readLine());
      }

    /**
     * Sets a new balance in the wallet
     *
     * @param  newBalance          new balance to write in the wallet
     */
    public void setBalance(int newBalance) throws Exception {
	this.file.setLength(0);
	String str = Integer.valueOf(newBalance).toString()+'\n'; 
	this.file.writeBytes(str); 
    }


    //Added a safe wallet withdrawal method with lock protection
    public boolean safeWithdraw(int valueToWithdraw) throws Exception {
          FileLock lock = file.getChannel().lock();
	  int Curbalance = getBalance();
	  try {
	  if (Curbalance >= valueToWithdraw) {
        try {
            Thread.sleep(10000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
	     this.setBalance(Curbalance - valueToWithdraw);
	     lock.release();
	     return true;
	  } else {
	      lock.release();
	      throw new Exception("Insufficient balance");
	     } 
	  } catch (Exception e) {
	      System.err.println(e);
	  }
	  return false;
    }

    /**
     * Closes the RandomAccessFile in this.file
     */
    public void close() throws Exception {
	this.file.close();
    }
}
