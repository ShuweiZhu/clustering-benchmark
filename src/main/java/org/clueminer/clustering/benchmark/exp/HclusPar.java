package org.clueminer.clustering.benchmark.exp;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.clueminer.clustering.aggl.HCLW;
import org.clueminer.clustering.aggl.HCLWMS;
import org.clueminer.clustering.aggl.HacLwMsPar;
import org.clueminer.clustering.api.AgglomerativeClustering;
import static org.clueminer.clustering.benchmark.Bench.ensureFolder;
import org.clueminer.clustering.benchmark.BenchParams;
import org.clueminer.clustering.benchmark.Experiment;

/**
 *
 * @author deric
 */
public class HclusPar extends Hclust {

    /**
     * @param args the command line arguments
     */
    @Override
    public void main(String[] args) {
        BenchParams params = parseArguments(args);
        setupLogging(params);

        benchmarkFolder = params.home + File.separatorChar + "benchmark" + File.separatorChar + "hclust-par";
        ensureFolder(benchmarkFolder);

        System.out.println("# n = " + params.n);
        System.out.println("=== starting experiment:");
        AgglomerativeClustering[] algorithms = new AgglomerativeClustering[]{
            new HCLW(), new HCLWMS(), new HacLwMsPar(4), new HacLwMsPar(8), new HacLwMsPar(16), new HacLwMsPar(32)
        };
        Experiment exp = new Experiment(params, benchmarkFolder, algorithms);
        ExecutorService execService = Executors.newFixedThreadPool(1);
        execService.submit(exp);
        execService.shutdown();
    }

}
