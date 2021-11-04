/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: F:\\Project\\anbar\\anbar\\fileDownloader\\src\\main\\aidl\\com\\liulishuo\\filedownloader\\i\\IFileDownloadIPCCallback.aidl
 */
package com.liulishuo.filedownloader.i;
public interface IFileDownloadIPCCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.liulishuo.filedownloader.i.IFileDownloadIPCCallback
{
private static final java.lang.String DESCRIPTOR = "com.liulishuo.filedownloader.i.IFileDownloadIPCCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.liulishuo.filedownloader.i.IFileDownloadIPCCallback interface,
 * generating a proxy if needed.
 */
public static com.liulishuo.filedownloader.i.IFileDownloadIPCCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.liulishuo.filedownloader.i.IFileDownloadIPCCallback))) {
return ((com.liulishuo.filedownloader.i.IFileDownloadIPCCallback)iin);
}
return new com.liulishuo.filedownloader.i.IFileDownloadIPCCallback.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
java.lang.String descriptor = DESCRIPTOR;
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(descriptor);
return true;
}
case TRANSACTION_callback:
{
data.enforceInterface(descriptor);
com.liulishuo.filedownloader.message.MessageSnapshot _arg0;
if ((0!=data.readInt())) {
_arg0 = com.liulishuo.filedownloader.message.MessageSnapshot.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.callback(_arg0);
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements com.liulishuo.filedownloader.i.IFileDownloadIPCCallback
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void callback(com.liulishuo.filedownloader.message.MessageSnapshot snapshot) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((snapshot!=null)) {
_data.writeInt(1);
snapshot.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_callback, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_callback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void callback(com.liulishuo.filedownloader.message.MessageSnapshot snapshot) throws android.os.RemoteException;
}
