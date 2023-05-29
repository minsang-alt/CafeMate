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

    public CustomerDto findOne(Long customerId){
        return entityToDto(customerRepository.findById(customerId).get());
    }

    public List<CustomerDto> findAll(){
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = false)
    public void updateOne(Long id, CustomerUpdateDto updateParam){
        customerRepository.update(id, updateParam);
    }

    @Transactional(readOnly = false)
    public void deleteOne(Long id){
        customerRepository.deleteById(id);
    }


    //회원 상태 active에서 deleted(1=>0)으로 바꿈
    @Transactional(readOnly = false)
    public void updateStatusCustomer(Long customerId){
       Optional<Customer> findCustomer = customerRepository.findById(customerId);

       if(findCustomer.isEmpty()){
           throw new IllegalStateException("회원이 존재하지 않습니다.");
       }

       CustomerUpdateDto customerUpdateDto = new CustomerUpdateDto();

        customerUpdateDto.setStatus(false);

        customerRepository.update(customerId,customerUpdateDto);
    }

    private void validateDuplicateCustomer(String customerId){
        Optional<Customer> result = customerRepository.findByCustomerId(customerId);

        if(!result.isEmpty()){
            throw new IllegalStateException("이미 가입된 고객입니다.");
        }
    }

    private Customer dtoToEntity(CustomerDto dto){
        return new Customer(dto.getCustomerId(), dto.getEMail(), dto.getPassword(),
                dto.getName(), dto.getPhoneNumber(), dto.getAlias(), dto.getSavedPoint(),dto.getStatus());
    }

    //비밀번호 안넘기기?
    private CustomerDto entityToDto(Customer customer){
        return new CustomerDto(customer.getId(),customer.getCustomerId(), customer.getEMail(), customer.getPassword(),
                customer.getName(), customer.getPhoneNumber(), customer.getAlias(), customer.getSavedPoint(),customer.getStatus());
    }
}
