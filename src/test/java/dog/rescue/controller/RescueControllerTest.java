package dog.rescue.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import dog.rescue.DogRescueApplication;
import dog.rescue.controller.model.LocationData;

@SpringBootTest(webEnvironment = WebEnvironment.NONE,
classes = DogRescueApplication.class)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@SqlConfig(encoding = "utf-8")

class RescueControllerTest extends RescueServiceTestSupport{

	@Test
	void testInsertLocation() {
		
		LocationData request = buildInsertLocation(1);
		LocationData expected = buildInsertLocation(1);
		
		LocationData actual = insertLocation(request);
		
		assertThat(actual).isEqualTo(expected);
		
		assertThat(rowsInLocationTable()).isOne();
	}

	@Test
	void testRetrieveLoaction() {
		
		LocationData location = insertLocation(buildInsertLocation(1));
		LocationData expected = buildInsertLocation(1);
		
		LocationData actual = retrieveLocation(location.getLocationId());
		
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void testRetrieveAllLocations() {
		
	  List<LocationData> expected = insertTwoLocations();
	  
	  List<LocationData> actual = retrieveAllLocations();
	  
	  assertThat(sorted(actual)).isEqualTo(sorted(expected));
	  
	
	}
	
	@Test
	void testUpdateLocation() {
		
	  insertLocation (buildInsertLocation(1));
	  LocationData expected = buildUpdatedLocation();
	  
	  LocationData actual = updateLocation(expected);
	  
	  assertThat(actual).isEqualTo(expected);
	  
	  assertThat(rowsInLocationTable()).isOne();
	  
	}

	@Test
	void testDeleteLocationWithDogs() {
		
		LocationData location = insertLocation(buildInsertLocation(1));
		Long locationId = location.getLocationId();
		
		insertDog(1);
		insertDog(2);
		
		assertThat(rowsInLocationTable()).isOne();
		assertThat(rowsInDogTable()).isEqualTo(2);
		assertThat(rowsInDogBreedTable()).isEqualTo(4);
		int breedRows = rowsInBreedTable();
		
		deleteLocation (locationId);
		
		assertThat(rowsInLocationTable()).isZero();
		assertThat(rowsInDogTable()).isZero();
		assertThat(rowsInDogBreedTable()).isZero();
		
		assertThat(rowsInBreedTable()).isEqualTo(breedRows);
	}

	

	
	


	
	
	

}
