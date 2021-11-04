/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: F:\\Project\\anbar\\anbar\\fileDownloader\\src\\main\\aidl\\com\\liulishuo\\filedownloader\\i\\IFileDownloadIPCService.aidl
 */
package com.liulishuo.filedownloader.i;
public interface IFileDownloadIPCService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.liulishuo.filedownloader.i.IFileDownloadIPCService
{
private static final java.lang.String DESCRIPTOR = "com.liulishuo.filedownloader.i.IFileDownloadIPCService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.liulishuo.filedownloader.i.IFileDownloadIPCService interface,
 * generating a proxy if needed.
 */
public static com.liulishuo.filedownloader.i.IFileDownloadIPCService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.liulishuo.filedownloader.i.IFileDownloadIPCService))) {
return ((com.liulishuo.filedownloader.i.IFileDownloadIPCService)iin);
}
return new com.liulishuo.filedownloader.i.IFileDownloadIPCService.Stub.Proxy(obj);
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
case TRANSACTION_registerCallback:
{
data.enforceInterface(descriptor);
com.liulishuo.filedownloader.i.IFileDownloadIPCCallback _arg0;
_arg0 = com.liulishuo.filedownloader.i.IFileDownloadIPCCallback.Stub.asInterface(data.readStrongBinder());
this.registerCallback(_arg0);
return true;
}
case TRANSACTION_unregisterCallback:
{
data.enforceInterface(descriptor);
com.liulishuo.filedownloader.i.IFileDownloadIPCCallback _arg0;
_arg0 = com.liulishuo.filedownloader.i.IFileDownloadIPCCallback.Stub.asInterface(data.readStrongBinder());
this.unregisterCallback(_arg0);
return true;
}
case TRANSACTION_checkDownloading:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.checkDownloading(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_start:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _arg2;
_arg2 = (0!=data.readInt());
int _arg3;
_arg3 = data.readInt();
int _arg4;
_arg4 = data.readInt();
int _arg5;
_arg5 = data.readInt();
boolean _arg6;
_arg6 = (0!=data.readInt());
com.liulishuo.filedownloader.model.FileDownloadHeader _arg7;
if ((0!=data.readInt())) {
_arg7 = com.liulishuo.filedownloader.model.FileDownloadHeader.CREATOR.createFromParcel(data);
}
else {
_arg7 = null;
}
boolean _arg8;
_arg8 = (0!=data.readInt());
this.start(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7, _arg8);
reply.writeNoException();
return true;
}
case TRANSACTION_pause:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.pause(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_pauseAllTasks:
{
data.enforceInterface(descriptor);
this.pauseAllTasks();
reply.writeNoException();
return true;
}
case TRANSACTION_setMaxNetworkThreadCount:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.setMaxNetworkThreadCount(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getSofar:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
long _result = this.getSofar(_arg0);
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_getTotal:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
long _result = this.getTotal(_arg0);
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_getStatus:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
byte _result = this.getStatus(_arg0);
reply.writeNoException();
reply.writeByte(_result);
return true;
}
case TRANSACTION_isIdle:
{
data.enforceInterface(descriptor);
boolean _result = this.isIdle();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_startForeground:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
android.app.Notification _arg1;
if ((0!=data.readInt())) {
_arg1 = android.app.Notification.CREATOR.createFromParcel(data);
}
else {
_arg1 = null;
}
this.startForeground(_arg0, _arg1);
return true;
}
case TRANSACTION_stopForeground:
{
data.enforceInterface(descriptor);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.stopForeground(_arg0);
return true;
}
case TRANSACTION_clearTaskData:
{
data.enforceInterface(descriptor);
int _arg0;
_arg0 = data.readInt();
boolean _result = this.clearTaskData(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_clearAllTaskData:
{
data.enforceInterface(descriptor);
this.clearAllTaskData();
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements com.liulishuo.filedownloader.i.IFileDownloadIPCService
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
@Override public void registerCallback(com.liulishuo.filedownloader.i.IFileDownloadIPCCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerCallback, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void unregisterCallback(com.liulishuo.filedownloader.i.IFileDownloadIPCCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public boolean checkDownloading(java.lang.String url, java.lang.String path) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(url);
_data.writeString(path);
mRemote.transact(Stub.TRANSACTION_checkDownloading, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
// why not use `oneway` to optimize the performance of the below `start` method? because if we
// use `oneway` it will be very hard to decide how is the binder thread going according to the context.
// and in this way(not is `oneway`), we can block the download before its launch only
// by {@link FileDownloadEventPool#shutdownSendPool} according to the context, because it
// will execute sync on the {@link FileDownloadEventPool#sendPool}

@Override public void start(java.lang.String url, java.lang.String path, boolean pathAsDirectory, int callbackProgressTimes, int callbackProgressMinIntervalMillis, int autoRetryTimes, boolean forceReDownload, com.liulishuo.filedownloader.model.FileDownloadHeader header, boolean isWifiRequired) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(url);
_data.writeString(path);
_data.writeInt(((pathAsDirectory)?(1):(0)));
_data.writeInt(callbackProgressTimes);
_data.writeInt(callbackProgressMinIntervalMillis);
_data.writeInt(autoRetryTimes);
_data.writeInt(((forceReDownload)?(1):(0)));
if ((header!=null)) {
_data.writeInt(1);
header.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
_data.writeInt(((isWifiRequired)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_start, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean pause(int downloadId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(downloadId);
mRemote.transact(Stub.TRANSACTION_pause, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void pauseAllTasks() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_pauseAllTasks, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean setMaxNetworkThreadCount(int count) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(count);
mRemote.transact(Stub.TRANSACTION_setMaxNetworkThreadCount, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public long getSofar(int downloadId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(downloadId);
mRemote.transact(Stub.TRANSACTION_getSofar, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public long getTotal(int downloadId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(downloadId);
mRemote.transact(Stub.TRANSACTION_getTotal, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public byte getStatus(int downloadId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
byte _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(downloadId);
mRemote.transact(Stub.TRANSACTION_getStatus, _data, _reply, 0);
_reply.readException();
_result = _reply.readByte();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean isIdle() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isIdle, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void startForeground(int id, android.app.Notification notification) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(id);
if ((notification!=null)) {
_data.writeInt(1);
notification.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_startForeground, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public void stopForeground(boolean removeNotification) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((removeNotification)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_stopForeground, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
@Override public boolean clearTaskData(int id) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(id);
mRemote.transact(Stub.TRANSACTION_clearTaskData, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void clearAllTaskData() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_clearAllTaskData, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unregisterCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_checkDownloading = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_start = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_pause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_pauseAllTasks = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_setMaxNetworkThreadCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_getSofar = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_getTotal = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_isIdle = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_startForeground = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_stopForeground = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_clearTaskData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_clearAllTaskData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
}
public void registerCallback(com.liulishuo.filedownloader.i.IFileDownloadIPCCallback callback) throws android.os.RemoteException;
public void unregisterCallback(com.liulishuo.filedownloader.i.IFileDownloadIPCCallback callback) throws android.os.RemoteException;
public boolean checkDownloading(java.lang.String url, java.lang.String path) throws android.os.RemoteException;
// why not use `oneway` to optimize the performance of the below `start` method? because if we
// use `oneway` it will be very hard to decide how is the binder thread going according to the context.
// and in this way(not is `oneway`), we can block the download before its launch only
// by {@link FileDownloadEventPool#shutdownSendPool} according to the context, because it
// will execute sync on the {@link FileDownloadEventPool#sendPool}

public void start(java.lang.String url, java.lang.String path, boolean pathAsDirectory, int callbackProgressTimes, int callbackProgressMinIntervalMillis, int autoRetryTimes, boolean forceReDownload, com.liulishuo.filedownloader.model.FileDownloadHeader header, boolean isWifiRequired) throws android.os.RemoteException;
public boolean pause(int downloadId) throws android.os.RemoteException;
public void pauseAllTasks() throws android.os.RemoteException;
public boolean setMaxNetworkThreadCount(int count) throws android.os.RemoteException;
public long getSofar(int downloadId) throws android.os.RemoteException;
public long getTotal(int downloadId) throws android.os.RemoteException;
public byte getStatus(int downloadId) throws android.os.RemoteException;
public boolean isIdle() throws android.os.RemoteException;
public void startForeground(int id, android.app.Notification notification) throws android.os.RemoteException;
public void stopForeground(boolean removeNotification) throws android.os.RemoteException;
public boolean clearTaskData(int id) throws android.os.RemoteException;
public void clearAllTaskData() throws android.os.RemoteException;
}
