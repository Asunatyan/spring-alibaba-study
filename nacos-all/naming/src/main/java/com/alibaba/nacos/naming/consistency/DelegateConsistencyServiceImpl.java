/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
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

package com.alibaba.nacos.naming.consistency;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.naming.consistency.ephemeral.EphemeralConsistencyService;
import com.alibaba.nacos.naming.consistency.persistent.PersistentConsistencyServiceDelegateImpl;
import com.alibaba.nacos.naming.pojo.Record;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

/**
 * Consistency delegate.
 *
 * @author nkorange
 * @since 1.0.0
 */
@DependsOn("ProtocolManager")
@Service("consistencyDelegate")
public class DelegateConsistencyServiceImpl implements ConsistencyService {

    private final PersistentConsistencyServiceDelegateImpl persistentConsistencyService;

    private final EphemeralConsistencyService ephemeralConsistencyService;

    public DelegateConsistencyServiceImpl(PersistentConsistencyServiceDelegateImpl persistentConsistencyService,
            EphemeralConsistencyService ephemeralConsistencyService) {
        this.persistentConsistencyService = persistentConsistencyService;
        this.ephemeralConsistencyService = ephemeralConsistencyService;
    }

    @Override
    public void put(String key, Record value) throws NacosException {

        //mapConsistencyService(key)的返回值来确认调用那个put
        mapConsistencyService(key).put(key, value);
    }

    @Override
    public void remove(String key) throws NacosException {
        mapConsistencyService(key).remove(key);
    }

    @Override
    public Datum get(String key) throws NacosException {
        return mapConsistencyService(key).get(key);
    }

    @Override
    public void listen(String key, RecordListener listener) throws NacosException {

        // this special key is listened by both:
        if (KeyBuilder.SERVICE_META_KEY_PREFIX.equals(key)) {
            persistentConsistencyService.listen(key, listener);
            ephemeralConsistencyService.listen(key, listener);
            return;
        }

        mapConsistencyService(key).listen(key, listener);
    }

    @Override
    public void unListen(String key, RecordListener listener) throws NacosException {
        mapConsistencyService(key).unListen(key, listener);
    }

    @Override
    public boolean isAvailable() {
        return ephemeralConsistencyService.isAvailable() && persistentConsistencyService.isAvailable();
    }

    private ConsistencyService mapConsistencyService(String key) {
        //一致性(Consistency, C)、可用性(Availability, A)、分区容错性(Partition Tolerance, P)三者不可兼得
        //       ephemeralConsistencyService AP架构临时实例的注册
        //       persistentConsistencyService CP架构持久实例的注册
        return KeyBuilder.matchEphemeralKey(key) ? ephemeralConsistencyService : persistentConsistencyService;
        //ephemeralConsistencyService 还是看不出来具体是那个,因为是接口需要点进去----->DistroConsistencyServiceImpl imp EphemeralConsistencyService
    }
}
