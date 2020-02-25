// ILocalMessageCallBack.aidl
package com.kk.hub;

import com.kk.hub.model.AIDLResultModel;
// Declare any non-default types here with import statements

interface ILocalMessageCallBack {
   void sendResult(in AIDLResultModel result);
}
