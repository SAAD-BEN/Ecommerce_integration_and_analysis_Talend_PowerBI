/*******************************************************************************
 *  Copyright (c) 2011-2019 Talend Inc. - www.talend.com
 *  All rights reserved.
 *
 *  This program and the accompanying materials are made available
 *  under the terms of the Apache License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 ******************************************************************************/
package routines.system.api;

/**
 * A JOB interface for Jobs that are using tESB Components
 */
public interface TalendESBJob extends TalendJob {

    /**
     * Returns {@link ESBEndpointInfo} instance
     * that describes the endpoint implemented by given Job.
     *
     * This method should return <code>null</code> if given Job
     * does not have any tESB provider component.
     *
     * @return {@link ESBEndpointInfo} or null if no provider is configured for the Job
     */
    ESBEndpointInfo getEndpoint();

    /**
     * Injecting a {@link ESBEndpointRegistry} to allow
     * tESB Consumer components to lookup and call ESB providers.
     *
     * @param callback
     */
    void setEndpointRegistry(ESBEndpointRegistry registry);

    /**
     * Injecting a {@link ESBProviderCallback} to allow
     * tESB Provider components read requests sent to the
     * {@link Job} and write responses from the {@link Job}
     *
     * @param callback
     */
    void setProviderCallback(ESBProviderCallback callback);
}
