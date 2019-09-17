package com.neo.neo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BasicRepository extends CrudRepository<BasicClass, Long> {
    BasicClass findByName(String name);
    @Query(value="MATCH(n:CFile) RETURN n")
    BasicClass findCfile();
    @Query(value="MATCH(n:DataCFile) RETURN n")
    List<DataCFile> findDataCfile();
    @Query(value="MATCH(n:Piece) \n WHERE n.name={0} \n RETURN n")
    BasicClass findPiece(String s);

    @Query(value="MATCH(n:Sentence) \n WHERE n.name={0} \n RETURN n")
    BasicClass findSentence(String s);

    @Query(value="MATCH (n:CFile)\n" +
            "WITH n.name AS name, COLLECT(n) AS nodelist, COUNT(*) AS count\n" +
            "WHERE count > 1\n" +
            "CALL apoc.refactor.mergeNodes(nodelist) YIELD node\n" +
            "RETURN node")
    void uniqueCFile();

    @Query(value="MATCH (n:Piece)\n" +
            "WITH n.name AS name, COLLECT(n) AS nodelist, COUNT(*) AS count\n" +
            "WHERE count > 1\n" +
            "CALL apoc.refactor.mergeNodes(nodelist) YIELD node\n" +
            "RETURN node\n")
    void uniquePiece();
    @Query(value="MATCH (n:Sentence)\n" +
            "WITH n.name AS name, COLLECT(n) AS nodelist, COUNT(*) AS count\n" +
            "WHERE count > 1\n" +
            "CALL apoc.refactor.mergeNodes(nodelist) YIELD node\n" +
            "RETURN node\n")
    void uniqueSentence();
    @Query(value="MATCH (a)-[r:CONTAIN]->(b)\n" +
            "WITH a, b, TAIL (COLLECT (r)) as rr\n" +
            "WHERE size(rr)>0\n" +
            "FOREACH (r IN rr | DELETE r)")
    void uniqueContain();


    @Query(value="MATCH(n:DataCFile) \n WHERE n.checked={checked} \n RETURN n")
    List<DataCFile> findFileChecked(String checked);

    @Query(value="MATCH(n)-[contain]->(m) \n WHERE id(n)={0} \n RETURN m")
    List<BasicClass> findSon(Long id);
    @Query(value="MATCH(n:DataValue) \n WHERE n.name={0} \n RETURN n")
    DataValue findValue(String value);

    @Query(value="MATCH(n:DataValue)-[x:pair]-(m:DataKey) \n WHERE n.name={0} AND m.name={1} \n RETURN m")
    DataKey findKey(String name,String key);

    @Query(value="MATCH(n:Sentence)-[x:pair]-(m:Sentence) \n WHERE n.name={0} AND m.name={1} \n RETURN m")
    Sentence findPair(String name,String key);

    @Query(value="match (n : DataValue)-[x:pair]-(m:DataKey) delete n,x,m ")
    void deleteAllVPK();
    @Query(value="MATCH(n:DataValue)  \n RETURN n")
    List<DataValue> findAllValue();
    @Query(value="MATCH(n:DataValue)-[pair]->(m:DataKey)  \n WHERE n.name={0} RETURN m")
    List<DataKey> findAllKey(String name);


}
