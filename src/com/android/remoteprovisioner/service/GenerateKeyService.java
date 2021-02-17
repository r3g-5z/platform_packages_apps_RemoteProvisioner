/**
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.remoteprovisioner.service;

import com.android.remoteprovisioner.Provisioner;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.security.remoteprovisioning.AttestationPoolStatus;
import android.security.remoteprovisioning.IRemoteProvisioning;
import android.util.Log;

/**
 * Provides the implementation for IGenerateKeyService.aidl
 */
public class GenerateKeyService extends Service {
    private static final String SERVICE = "android.security.remoteprovisioning";
    private static final String TAG = "RemoteProvisioningService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private final IGenerateKeyService.Stub binder = new IGenerateKeyService.Stub() {
        @Override
        public void generateKey() {
            try {
                IRemoteProvisioning binder =
                        IRemoteProvisioning.Stub.asInterface(ServiceManager.getService(SERVICE));
                AttestationPoolStatus pool =
                        binder.getPoolStatus(1);
                if (pool.unassigned == 0) {
                    binder.generateKeyPair(false /* isTestMode */);
                    Provisioner.provisionCerts(1 /* numCsr */, binder);
                } else {
                    Log.e(TAG, "generateKey() called, but signed certs are available.");
                }
            } catch (RemoteException e) {
                Log.e(TAG, "Remote Exception: ", e);
            }
        }
    };
}
