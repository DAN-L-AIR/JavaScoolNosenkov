package hw8;

import java.util.Date;
import java.util.List;

import static hw8._cacheType.IN_FILE;
import static hw8._cacheType.IN_MEMORY;

public interface Service {
        @Cache(cacheType = IN_FILE, fileNamePrefix = "run_cache.dat", zip = true, identityBy = {String.class, double.class})
        List<String> run(String item, double value, Date date);

        @Cache(cacheType = IN_MEMORY, fileNamePrefix = "work_cache.dat", listMaxLength = 100_000)
        List<String> work(String item);
}
