package jpabook.jpashop11.service;

import jpabook.jpashop11.domain.Delivery;
import jpabook.jpashop11.domain.Member;
import jpabook.jpashop11.domain.Order;
import jpabook.jpashop11.domain.OrderItem;
import jpabook.jpashop11.domain.item.Item;
import jpabook.jpashop11.repository.ItemRepository;
import jpabook.jpashop11.repository.MemberRepository;
import jpabook.jpashop11.repository.OrderRepository;
import jpabook.jpashop11.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    /**
     * Order
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // Entity
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);
        // create delivery
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        // create OrderItem
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        // create Order
        Order order = Order.createOrder(member, delivery, orderItem);
        // save order
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * Cancel
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    // Search
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
