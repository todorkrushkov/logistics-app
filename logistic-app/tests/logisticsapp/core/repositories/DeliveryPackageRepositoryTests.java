package logisticsapp.core.repositories;

import logisticsapp.core.contracts.repositories.DeliveryPackageRepository;
import logisticsapp.core.enums.State;
import logisticsapp.core.exceptions.ElementNotFoundException;
import logisticsapp.models.contracts.Customer;
import logisticsapp.models.contracts.DeliveryPackage;
import logisticsapp.models.contracts.Location;
import logisticsapp.commands.enums.TransferType;
import logisticsapp.models.CustomerImpl;
import logisticsapp.models.LocationImpl;
import logisticsapp.models.enums.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryPackageRepositoryTests {
    DeliveryPackageRepository deliveryPackageRepository;
    Customer sender;
    Customer receiver;
    Location start;
    Location end;

    @BeforeEach
    public void beforeEach() {
        deliveryPackageRepository = new DeliveryPackageRepositoryImpl();

        sender = new CustomerImpl(1, "Todor", "Krushkov", "0888000001");
        receiver = new CustomerImpl(2, "Ivelin", "Yanev", "0888000002");
        start = new LocationImpl(1, City.SYD);
        end = new LocationImpl(2, City.MEL);
    }

    @Test
    public void createDeliveryPackage_Should_AddPackageWithIncrementedId() {
        DeliveryPackage dp = deliveryPackageRepository.createDeliveryPackage(sender, receiver, 3.0, start, end);

        assertEquals(100001, dp.getId());
        assertEquals(1, deliveryPackageRepository.getDeliveryPackages().size());
    }

    @Test
    public void findDeliveryPackageById_Should_ReturnCorrectPackage() {
        DeliveryPackage dp = deliveryPackageRepository.createDeliveryPackage(sender, receiver, 2.5, start, end);

        DeliveryPackage found = deliveryPackageRepository.findDeliveryPackageById(dp.getId());

        assertEquals(dp.getId(), found.getId());
        assertEquals(sender, found.getSender());
        assertEquals(receiver, found.getReceiver());
    }

    @Test
    public void findDeliveryPackageById_Should_Throw_When_NotFound() {
        assertThrows(ElementNotFoundException.class, () ->
                deliveryPackageRepository.findDeliveryPackageById(999999));
    }

    @Test
    public void deliveryPackageExists_Should_ReturnTrue_IfExists() {
        DeliveryPackage dp = deliveryPackageRepository.createDeliveryPackage(sender, receiver, 1.0, start, end);

        assertTrue(deliveryPackageRepository.deliveryPackageExists(dp.getId()));
    }

    @Test
    public void deliveryPackageExists_Should_ReturnFalse_IfNotExists() {
        assertFalse(deliveryPackageRepository.deliveryPackageExists(123456));
    }

    @Test
    public void changeState_Should_ChangeStateOfPackage() {
        DeliveryPackage dp = deliveryPackageRepository.createDeliveryPackage(sender, receiver, 5.0, start, end);

        deliveryPackageRepository.changeState(dp.getId(), State.DELIVERED);

        assertEquals(State.DELIVERED, dp.getState());
    }

    @Test
    public void findDeliveryPackagesByTransferType_Should_ReturnReceived_When_ReceiverMatch() {
        DeliveryPackage dp = deliveryPackageRepository.createDeliveryPackage(sender, receiver, 2.0, start, end);

        var result = deliveryPackageRepository.findDeliveryPackagesByTransferType(receiver, TransferType.RECEIVE);

        assertEquals(1, result.size());
        assertEquals(dp.getId(), result.get(0).getId());
    }

    @Test
    public void findDeliveryPackagesByTransferType_Should_ReturnSent_When_SenderMatch() {
        DeliveryPackage dp = deliveryPackageRepository.createDeliveryPackage(sender, receiver, 2.0, start, end);

        var result = deliveryPackageRepository.findDeliveryPackagesByTransferType(sender, TransferType.SENT);

        assertEquals(1, result.size());
        assertEquals(dp.getId(), result.get(0).getId());
    }
}