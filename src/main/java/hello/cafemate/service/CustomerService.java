package hello.cafemate.service;

import hello.cafemate.domain.Customer;
import hello.cafemate.web.dto.user.CustomerDto;
import hello.cafemate.dto.update_dto.CustomerUpdateDto;
import hello.cafemate.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional
    public Long join(CustomerDto dto){
        Customer customer = dtoToEntity(dto);
        validateDuplicateCustomer(customer.getCustomerId());
        Customer saved = customerRepository.save(customer);
        return saved.getId();
    }

    public CustomerDto findOne(CustomerDto dto){
        return entityToDto(customerRepository.findByCustomerId(dto.getCustomerId()).get());
    }

    public List<CustomerDto> findAll(){
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public void updateOne(Long id, CustomerUpdateDto updateParam){
        customerRepository.update(id, updateParam);
    }

    public void deleteOne(Long id){
        customerRepository.deleteById(id);
    }

    private void validateDuplicateCustomer(String customerId){
        Optional<Customer> result = customerRepository.findByCustomerId(customerId);

        if(!result.isEmpty()){
            throw new IllegalStateException("이미 가입된 고객입니다.");
        }
    }

    private Customer dtoToEntity(CustomerDto dto){
        return new Customer(dto.getCustomerId(), dto.getEMail(), dto.getPassword(),
                dto.getName(), dto.getPhoneNumber(), dto.getAlias(), dto.getSavedPoint());
    }

    private CustomerDto entityToDto(Customer customer){
        return new CustomerDto(customer.getId(),customer.getCustomerId(), customer.getEMail(), customer.getPassword(),
                customer.getName(), customer.getPhoneNumber(), customer.getAlias(), customer.getSavedPoint());
    }
}
