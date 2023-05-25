
package hello.cafemate.service;

import hello.cafemate.domain.Customer;
import hello.cafemate.domain.Point;
import hello.cafemate.dto.simple_dto.PointDto;
import hello.cafemate.dto.update_dto.PointUpdateDto;
import hello.cafemate.repository.CustomerRepository;
import hello.cafemate.repository.PointRepository;
import hello.cafemate.web.dto.user.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;


/**
     * CustomerRepository 필드를 가지므로 더 직관적, 효율적으로
     * Customer를 탐색하여 Point 적립 연산에 활용할 수 있다.
     * CustomerService를 사용할 경우, 같은 계층에 대한 부적절한 의존관계
     * (PointService -> CustomerService)가 발생한다.
     */

    private final CustomerRepository customerRepository;

    @Transactional
    public PointDto savePoint(CustomerDto customerDto, PointDto pointDto){
        Optional<Customer> findResult = customerRepository.findByCustomerId(customerDto.getCustomerId());
        if(findResult.isEmpty()){
            throw new IllegalStateException("고객 정보가 존재하지 않습니다.");
        }

        Long customerId = findResult.get().getId();
        pointDto.setCustomerId(customerId);

        Point saved = pointRepository.save(dtoToEntity(pointDto));
        return entityToDto(saved);
    }

    public List<PointDto> getCustomerPointHistory(CustomerDto customerDto){
        Optional<Customer> findResult =
                customerRepository.findByCustomerId(customerDto.getCustomerId());

        if(findResult.isEmpty()){
            throw new IllegalStateException("고객 정보가 존재하지 않습니다.");
        }

        Long customerId = findResult.get().getId();
        List<Point> points = pointRepository.findAllByCustomerId(customerId);
        List<PointDto> result =
                points.stream().map(this::entityToDto).collect(Collectors.toList());

        return result;
    }

    @Transactional
    public void updatePointHistory(PointDto pointDto, PointUpdateDto updateParam){
        List<Point> pointHistory = pointRepository.findAllByCustomerId(pointDto.getCustomerId());

        Optional<Point> foundPoint = pointHistory.stream()
                .filter(p -> Objects.equals(p.getAmount(), pointDto.getAmount())
                        && Objects.equals(p.getSavedDate(), pointDto.getSavedDate()))
                .findAny();

        if(foundPoint.isEmpty()){
            throw new IllegalStateException("해당 포인트 내역이 존재하지 않습니다.");
        }

        pointRepository.update(foundPoint.get().getId(), updateParam);
    }

    @Transactional
    public void deleteOne(PointDto pointDto){
        List<Point> pointHistory = pointRepository.findAllByCustomerId(pointDto.getCustomerId());

        Optional<Point> foundPoint = pointHistory.stream()
                .filter(p -> Objects.equals(p.getAmount(), pointDto.getAmount())
                        && Objects.equals(p.getSavedDate(), pointDto.getSavedDate()))
                .findAny();

        if(foundPoint.isEmpty()){
            throw new IllegalStateException("해당 포인트 내역이 존재하지 않습니다");
        }

        pointRepository.deleteById(foundPoint.get().getId());
    }

    public Integer getCustomerAccumulatedPoints(CustomerDto customerDto){
        Optional<Customer> findResult =
                customerRepository.findByCustomerId(customerDto.getCustomerId());

        if(findResult.isEmpty()){
            throw new IllegalStateException("고객 정보가 존재하지 않습니다");
        }

        Long customerId = findResult.get().getId();
        List<Point> points = pointRepository.findAllByCustomerId(customerId);

        return points.stream().mapToInt(Point::getAmount).sum();
    }

    private PointDto entityToDto(Point point){
        return new PointDto(
                point.getCustomerId(),
                point.getAmount(),
                point.getSavedDate()
        );
    }

    private Point dtoToEntity(PointDto pointDto){
        return new Point(
                pointDto.getCustomerId(),
                pointDto.getAmount(),
                pointDto.getSavedDate()
        );
    }
}

