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

/**
 * Interface to allow the framework to notify the RemoteProvisioner app when keys are empty. This
 * will be used if Keystore replies with an error code NO_KEYS_AVAILABLE in response to an
 * attestation request. The framework can then synchronously call generateKey() to get more
 * attestation keys generated and signed. Upon return, the caller can be certain an attestation key
 * is available.
 */
interface IGenerateKeyService {
    /** Ping the provisioner service to indicate there are no remaining attestation keys left. */
    void generateKey();
}
