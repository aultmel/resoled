package org.launchcode.liftoff.shoefinder.models.dto;

    import java.util.List;

    public class SearchListingsDTO {

        private String searchTerm;

        private List<String> brands;

        private List<String> sizes;

        private List<String> styles;

        private List<String> conditions;

        private List<String> genders;

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

        public List<String> getConditions() {
            return conditions;
        }

        public void setConditions(List<String> conditions) {
            this.conditions = conditions;
        }

        public List<String> getGenders() {
            return genders;
        }

        public void setGenders(List<String> genders) {
            this.genders = genders;
        }

    }


