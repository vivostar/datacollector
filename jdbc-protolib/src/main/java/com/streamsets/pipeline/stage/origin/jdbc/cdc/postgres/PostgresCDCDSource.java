/*
 * Copyright 2017 StreamSets Inc.
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
package com.streamsets.pipeline.stage.origin.jdbc.cdc.postgres;

import com.streamsets.pipeline.api.ConfigDefBean;
import com.streamsets.pipeline.api.ConfigGroups;
import com.streamsets.pipeline.api.GenerateResourceBundle;
import com.streamsets.pipeline.api.HideConfigs;
import com.streamsets.pipeline.api.Source;
import com.streamsets.pipeline.api.StageDef;
import com.streamsets.pipeline.api.base.configurablestage.DSourceOffsetCommitter;
import com.streamsets.pipeline.lib.jdbc.PostgresHikariPoolConfigBean;

@StageDef(
    version = 4,
    label = "PostgreSQL CDC Client",
    description = "Origin that reads change events from a PostgreSQL database",
    icon = "rdbms.png",
    recordsByRef = true,
    producesEvents = true,
    resetOffset = true,
    onlineHelpRefUrl ="index.html?contextID=task_v21_nm4_n2b",
    upgrader = PostgresCDCSourceUpgrader.class,
    upgraderDef = "upgrader/PostgresCDCSource.yaml"
)
@GenerateResourceBundle
@ConfigGroups(Groups.class)
@HideConfigs({
    "hikariConf.autoCommit",
    "postgresCDCConfigBean.baseConfigBean.caseSensitive",
    "postgresCDCConfigBean.baseConfigBean.changeTypes",
    "postgresCDCConfigBean.parseQuery",
    "postgresCDCConfigBean.decoderValue",
    "postgresCDCConfigBean.minVersion",
    "postgresCDCConfigBean.replicationType"
})
public class PostgresCDCDSource extends DSourceOffsetCommitter {

  @ConfigDefBean
  public PostgresHikariPoolConfigBean hikariConf = new PostgresHikariPoolConfigBean();

  @ConfigDefBean
  public PostgresCDCConfigBean postgresCDCConfigBean = new PostgresCDCConfigBean();

  @Override
  protected Source createSource() {
    return new PostgresCDCSource(hikariConf, postgresCDCConfigBean);
  }

}
