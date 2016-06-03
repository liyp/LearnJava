/*
 * Copyright © 2016 liyp (liyp.yunpeng@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.amazonaws.services.kinesis.samples.stocktrades.utils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

/**
 * Provides utilities for retrieving credentials to talk to AWS
 */
public class CredentialUtils {

    public static AWSCredentialsProvider getCredentialsProvider() throws Exception {
        /*
         * The ProfileCredentialsProvider will return your [default] credential profile by
         * reading from the credentials file located at (~/.aws/credentials).
         */
        AWSCredentialsProvider credentialsProvider = null;
        try {
            credentialsProvider = new ProfileCredentialsProvider("default");
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (~/.aws/credentials), and is in valid format.",
                    e);
        }
        return credentialsProvider;
    }

}
