package com.oracle.vehicle.cache;

import com.oracle.vehicle.config.ApplicationContextProvider;
import com.oracle.vehicle.model.persistence.VehicleDb;
import com.oracle.vehicle.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheEntry;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.writer.CacheWriter;
import net.sf.ehcache.writer.writebehind.operations.SingleOperationType;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class H2CacheWriter implements CacheWriter {

    private final VehicleRepository vehicleRepository;

    private final Ehcache cache;

    public H2CacheWriter(Ehcache cache) {
        this.cache = cache;
        this.vehicleRepository = (VehicleRepository) ApplicationContextProvider.getApplicationContext()
                                                                               .getBean("vehicleRepository");
    }

    @Override
    public CacheWriter clone(Ehcache cache) throws CloneNotSupportedException {
        log.error("H2CacheWriter can not be clonned");
        throw new CloneNotSupportedException("H2CacheWriter can not be clonned");
    }

    @Override
    public void init() {

    }

    @Override
    public void dispose() throws CacheException {

    }

    @Override
    public void write(Element element) throws CacheException {
        VehicleDb vehicle = (VehicleDb) element.getObjectValue();
        log.info("Saving vehicle: {}", vehicle);
        vehicleRepository.save(vehicle);
        cache.put(element);
    }

    @Override
    public void writeAll(Collection<Element> elements) throws CacheException {
        log.info("Saving vehicles batch");
        List<VehicleDb> vehiclesBatch = elements.stream()
                                                .map(element -> (VehicleDb) element.getObjectValue())
                                                .collect(Collectors.toList());
        vehicleRepository.saveAll(vehiclesBatch);
        cache.putAll(elements);
    }

    @Override
    public void delete(CacheEntry entry) throws CacheException {
        VehicleDb vehicle = (VehicleDb) entry.getElement().getObjectValue();
        log.info("Deleting vehicle: {}", vehicle);
        vehicleRepository.delete(vehicle);
        cache.remove(entry.getKey());
    }

    @Override
    public void deleteAll(Collection<CacheEntry> entries) throws CacheException {
        log.info("Deleting vehicles batch");
        List<VehicleDb> vehiclesBatch = entries.stream()
                                               .map(entry -> (VehicleDb) entry.getElement().getObjectValue())
                                               .collect(Collectors.toList());
        vehicleRepository.deleteAll(vehiclesBatch);
        cache.removeAll(entries.stream()
                               .map(CacheEntry::getKey)
                               .collect(Collectors.toList()));
    }

    @Override
    public void throwAway(Element element, SingleOperationType operationType, RuntimeException e) {

    }

}
