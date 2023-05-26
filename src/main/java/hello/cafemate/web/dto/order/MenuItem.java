package hello.cafemate.web.dto.order;

public class MenuItem {

        private String menu;
        private Integer qty;

        // 기본 생성자
        public MenuItem() {}

        // 인자가 있는 생성자
        public MenuItem(String menu, int qty) {
            this.menu = menu;
            this.qty = qty;
        }

        // Getter와 Setter 메소드
        public String getMenu() {
            return menu;
        }

        public void setMenu(String menu) {
            this.menu = menu;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }



}
