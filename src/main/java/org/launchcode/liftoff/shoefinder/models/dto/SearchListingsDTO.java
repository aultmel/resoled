package org.launchcode.liftoff.shoefinder.models.dto;

    import java.util.List;

    public class SearchListingsDTO {

        private String searchTerm;

        private List<String> brands;

        private List<String> sizes;

        private List<String> styles;

        private String condition;

        private List<String> genders;

//        private String zipCode;
//
//        private String distance;

        public SearchListingsDTO() {

        }

        public String getSearchTerm() {
            return searchTerm;
        }

        public void setSearchTerm(String searchTerm) {
            this.searchTerm = searchTerm;
        }

        public List<String> getBrands() {
            return brands;
        }

        public void setBrands(List<String> brands) {
            this.brands = brands;
        }

        public List<String> getSizes() {
            return sizes;
        }

        public void setSizes(List<String> sizes) {
            this.sizes = sizes;
        }

        public List<String> getStyles() {
            return styles;
        }

        public void setStyles(List<String> styles) {
            this.styles = styles;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public List<String> getGenders() {
            return genders;
        }

        public void setGenders(List<String> genders) {
            this.genders = genders;
        }

//        public String getZipCode() {
//            return zipCode;
//        }
//
//        public void setZipCode(String zipCode) {
//            this.zipCode = zipCode;
//        }
//
//        public String getDistance() {
//            return distance;
//        }
//
//        public void setDistance(String distance) {
//            this.distance = distance;
//        }
    }


