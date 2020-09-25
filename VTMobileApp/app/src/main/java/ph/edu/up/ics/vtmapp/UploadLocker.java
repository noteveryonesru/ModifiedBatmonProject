package ph.edu.up.ics.vtmapp;

public interface UploadLocker {
    public void lock();
    public void unlock();
    public void toastError(String s);
}
