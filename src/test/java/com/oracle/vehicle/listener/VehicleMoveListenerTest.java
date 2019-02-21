package com.oracle.vehicle.listener;

import com.oracle.vehicle.model.domain.Vehicle;
import com.oracle.vehicle.model.mapper.VehicleMapper;
import com.oracle.vehicle.model.persistence.VehicleDb;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VehicleMoveListenerTest {

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleMoveListener unit;

    @Test
    public void updateVehicle() {
        Vehicle vehicle = new Vehicle("VehicleName", 2.473f, 10f);
        VehicleDb vehicleDb = new VehicleDb("VehicleName", 2.473f, 10f);
        when(vehicleMapper.toVehicleDb(vehicle)).thenReturn(vehicleDb);
        when(cacheManager.getCache("default")).thenReturn(cache);

        unit.updateVehicle(vehicle);

        Element expected = new Element(vehicle.getVehicleName(), vehicleDb);
        verify(cache).putWithWriter(expected);
    }

}