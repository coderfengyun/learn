namespace java org.tcse.algorithms.learn.enhancer.thrift

struct TestData {
    1: string name,
    2: string serverId
}

service Test {
    TestData get(
        1: string name
    )

    oneway void save(
        1: TestData data
    )
}