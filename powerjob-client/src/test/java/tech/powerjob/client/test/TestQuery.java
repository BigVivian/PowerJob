package tech.powerjob.client.test;

import com.alibaba.fastjson.JSON;
import tech.powerjob.common.request.query.InstanceInfoQuery;
import tech.powerjob.common.request.query.JobInfoQuery;
import tech.powerjob.common.enums.ExecuteType;
import tech.powerjob.common.enums.ProcessorType;
import tech.powerjob.common.response.InstanceInfoDTO;
import tech.powerjob.common.response.JobInfoDTO;
import tech.powerjob.common.response.ResultDTO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Test the query method
 *
 * @author tjq
 * @since 1/16/21
 */
@Slf4j
class TestQuery extends ClientInitializer {

    @Test
    void testFetchAllJob() {
        ResultDTO<List<JobInfoDTO>> allJobRes = powerJobClient.fetchAllJob();
        System.out.println(JSON.toJSONString(allJobRes));
    }

    @Test
    void testQueryJob() {
        JobInfoQuery jobInfoQuery = new JobInfoQuery()
                .setIdGt(-1L)
                .setIdLt(10086L)
                .setJobNameLike("DAG")
                .setGmtModifiedGt(DateUtils.addYears(new Date(), -10))
                .setGmtCreateLt(DateUtils.addDays(new Date(), 10))
                .setExecuteTypeIn(Lists.newArrayList(ExecuteType.STANDALONE.getV(), ExecuteType.BROADCAST.getV(), ExecuteType.MAP_REDUCE.getV()))
                .setProcessorTypeIn(Lists.newArrayList(ProcessorType.BUILT_IN.getV(), ProcessorType.SHELL.getV(), ProcessorType.EXTERNAL.getV()))
                .setProcessorInfoLike("tech.powerjob");

        ResultDTO<List<JobInfoDTO>> jobQueryResult = powerJobClient.queryJob(jobInfoQuery);
        System.out.println(JSON.toJSONString(jobQueryResult));
        System.out.println(jobQueryResult.getData().size());
    }

    @Test
    void testQueryInstance() {
        InstanceInfoQuery qry = new InstanceInfoQuery()
                .setJobIdGt(-1L)
                .setJobIdLt(10086L)
                .setInstanceIdGt(-1L)
                .setInstanceIdGt(10086L)
                .setJobParamsLike("aa")
                .setTypeIn(Arrays.asList(1,2))
                .setStatusIn(Arrays.asList(3,4))
                .setResultLike("no worker available")
                .setExpectedTriggerTimeGt(1693994740089L)
                .setActualTriggerTimeGt(1693994740089L)
                .setFinishedTimeGt(1693994740089L)
                .setGmtCreateGt(new Date(1693994740089L))
                .setGmtModifiedGt(new Date(1693994740089L));
        ResultDTO<List<InstanceInfoDTO>> instanceQryResult = powerJobClient.queryInstance(qry);
        System.out.println(instanceQryResult);
    }
}
