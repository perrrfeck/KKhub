// ILocalMessage.aidl
package com.kk.hub;

// Declare any non-default types here with import statements
import com.kk.hub.ILocalMessageCallBack;
interface ILocalMessage {

  void sendMessage(in String message);

  int getVersion();

  void registerCallBack(in ILocalMessageCallBack callback);

}
