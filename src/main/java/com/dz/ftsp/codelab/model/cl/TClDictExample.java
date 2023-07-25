package com.dz.ftsp.codelab.model.cl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TClDictExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TClDictExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateIdIsNull() {
            addCriterion("create_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateIdIsNotNull() {
            addCriterion("create_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateIdEqualTo(Long value) {
            addCriterion("create_id =", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotEqualTo(Long value) {
            addCriterion("create_id <>", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdGreaterThan(Long value) {
            addCriterion("create_id >", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdGreaterThanOrEqualTo(Long value) {
            addCriterion("create_id >=", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdLessThan(Long value) {
            addCriterion("create_id <", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdLessThanOrEqualTo(Long value) {
            addCriterion("create_id <=", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdIn(List<Long> values) {
            addCriterion("create_id in", values, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotIn(List<Long> values) {
            addCriterion("create_id not in", values, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdBetween(Long value1, Long value2) {
            addCriterion("create_id between", value1, value2, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotBetween(Long value1, Long value2) {
            addCriterion("create_id not between", value1, value2, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateNickNameIsNull() {
            addCriterion("create_nick_name is null");
            return (Criteria) this;
        }

        public Criteria andCreateNickNameIsNotNull() {
            addCriterion("create_nick_name is not null");
            return (Criteria) this;
        }

        public Criteria andCreateNickNameEqualTo(String value) {
            addCriterion("create_nick_name =", value, "createNickName");
            return (Criteria) this;
        }

        public Criteria andCreateNickNameNotEqualTo(String value) {
            addCriterion("create_nick_name <>", value, "createNickName");
            return (Criteria) this;
        }

        public Criteria andCreateNickNameGreaterThan(String value) {
            addCriterion("create_nick_name >", value, "createNickName");
            return (Criteria) this;
        }

        public Criteria andCreateNickNameGreaterThanOrEqualTo(String value) {
            addCriterion("create_nick_name >=", value, "createNickName");
            return (Criteria) this;
        }

        public Criteria andCreateNickNameLessThan(String value) {
            addCriterion("create_nick_name <", value, "createNickName");
            return (Criteria) this;
        }

        public Criteria andCreateNickNameLessThanOrEqualTo(String value) {
            addCriterion("create_nick_name <=", value, "createNickName");
            return (Criteria) this;
        }

        public Criteria andCreateNickNameLike(String value) {
            addCriterion("create_nick_name like", value, "createNickName");
            return (Criteria) this;
        }

        public Criteria andCreateNickNameNotLike(String value) {
            addCriterion("create_nick_name not like", value, "createNickName");
            return (Criteria) this;
        }

        public Criteria andCreateNickNameIn(List<String> values) {
            addCriterion("create_nick_name in", values, "createNickName");
            return (Criteria) this;
        }

        public Criteria andCreateNickNameNotIn(List<String> values) {
            addCriterion("create_nick_name not in", values, "createNickName");
            return (Criteria) this;
        }

        public Criteria andCreateNickNameBetween(String value1, String value2) {
            addCriterion("create_nick_name between", value1, value2, "createNickName");
            return (Criteria) this;
        }

        public Criteria andCreateNickNameNotBetween(String value1, String value2) {
            addCriterion("create_nick_name not between", value1, value2, "createNickName");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateIdIsNull() {
            addCriterion("update_id is null");
            return (Criteria) this;
        }

        public Criteria andUpdateIdIsNotNull() {
            addCriterion("update_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateIdEqualTo(Long value) {
            addCriterion("update_id =", value, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdNotEqualTo(Long value) {
            addCriterion("update_id <>", value, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdGreaterThan(Long value) {
            addCriterion("update_id >", value, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdGreaterThanOrEqualTo(Long value) {
            addCriterion("update_id >=", value, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdLessThan(Long value) {
            addCriterion("update_id <", value, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdLessThanOrEqualTo(Long value) {
            addCriterion("update_id <=", value, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdIn(List<Long> values) {
            addCriterion("update_id in", values, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdNotIn(List<Long> values) {
            addCriterion("update_id not in", values, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdBetween(Long value1, Long value2) {
            addCriterion("update_id between", value1, value2, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateIdNotBetween(Long value1, Long value2) {
            addCriterion("update_id not between", value1, value2, "updateId");
            return (Criteria) this;
        }

        public Criteria andUpdateNickNameIsNull() {
            addCriterion("update_nick_name is null");
            return (Criteria) this;
        }

        public Criteria andUpdateNickNameIsNotNull() {
            addCriterion("update_nick_name is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateNickNameEqualTo(String value) {
            addCriterion("update_nick_name =", value, "updateNickName");
            return (Criteria) this;
        }

        public Criteria andUpdateNickNameNotEqualTo(String value) {
            addCriterion("update_nick_name <>", value, "updateNickName");
            return (Criteria) this;
        }

        public Criteria andUpdateNickNameGreaterThan(String value) {
            addCriterion("update_nick_name >", value, "updateNickName");
            return (Criteria) this;
        }

        public Criteria andUpdateNickNameGreaterThanOrEqualTo(String value) {
            addCriterion("update_nick_name >=", value, "updateNickName");
            return (Criteria) this;
        }

        public Criteria andUpdateNickNameLessThan(String value) {
            addCriterion("update_nick_name <", value, "updateNickName");
            return (Criteria) this;
        }

        public Criteria andUpdateNickNameLessThanOrEqualTo(String value) {
            addCriterion("update_nick_name <=", value, "updateNickName");
            return (Criteria) this;
        }

        public Criteria andUpdateNickNameLike(String value) {
            addCriterion("update_nick_name like", value, "updateNickName");
            return (Criteria) this;
        }

        public Criteria andUpdateNickNameNotLike(String value) {
            addCriterion("update_nick_name not like", value, "updateNickName");
            return (Criteria) this;
        }

        public Criteria andUpdateNickNameIn(List<String> values) {
            addCriterion("update_nick_name in", values, "updateNickName");
            return (Criteria) this;
        }

        public Criteria andUpdateNickNameNotIn(List<String> values) {
            addCriterion("update_nick_name not in", values, "updateNickName");
            return (Criteria) this;
        }

        public Criteria andUpdateNickNameBetween(String value1, String value2) {
            addCriterion("update_nick_name between", value1, value2, "updateNickName");
            return (Criteria) this;
        }

        public Criteria andUpdateNickNameNotBetween(String value1, String value2) {
            addCriterion("update_nick_name not between", value1, value2, "updateNickName");
            return (Criteria) this;
        }

        public Criteria andNodeKeyIsNull() {
            addCriterion("node_key is null");
            return (Criteria) this;
        }

        public Criteria andNodeKeyIsNotNull() {
            addCriterion("node_key is not null");
            return (Criteria) this;
        }

        public Criteria andNodeKeyEqualTo(String value) {
            addCriterion("node_key =", value, "nodeKey");
            return (Criteria) this;
        }

        public Criteria andNodeKeyNotEqualTo(String value) {
            addCriterion("node_key <>", value, "nodeKey");
            return (Criteria) this;
        }

        public Criteria andNodeKeyGreaterThan(String value) {
            addCriterion("node_key >", value, "nodeKey");
            return (Criteria) this;
        }

        public Criteria andNodeKeyGreaterThanOrEqualTo(String value) {
            addCriterion("node_key >=", value, "nodeKey");
            return (Criteria) this;
        }

        public Criteria andNodeKeyLessThan(String value) {
            addCriterion("node_key <", value, "nodeKey");
            return (Criteria) this;
        }

        public Criteria andNodeKeyLessThanOrEqualTo(String value) {
            addCriterion("node_key <=", value, "nodeKey");
            return (Criteria) this;
        }

        public Criteria andNodeKeyLike(String value) {
            addCriterion("node_key like", value, "nodeKey");
            return (Criteria) this;
        }

        public Criteria andNodeKeyNotLike(String value) {
            addCriterion("node_key not like", value, "nodeKey");
            return (Criteria) this;
        }

        public Criteria andNodeKeyIn(List<String> values) {
            addCriterion("node_key in", values, "nodeKey");
            return (Criteria) this;
        }

        public Criteria andNodeKeyNotIn(List<String> values) {
            addCriterion("node_key not in", values, "nodeKey");
            return (Criteria) this;
        }

        public Criteria andNodeKeyBetween(String value1, String value2) {
            addCriterion("node_key between", value1, value2, "nodeKey");
            return (Criteria) this;
        }

        public Criteria andNodeKeyNotBetween(String value1, String value2) {
            addCriterion("node_key not between", value1, value2, "nodeKey");
            return (Criteria) this;
        }

        public Criteria andNodePathIsNull() {
            addCriterion("node_path is null");
            return (Criteria) this;
        }

        public Criteria andNodePathIsNotNull() {
            addCriterion("node_path is not null");
            return (Criteria) this;
        }

        public Criteria andNodePathEqualTo(String value) {
            addCriterion("node_path =", value, "nodePath");
            return (Criteria) this;
        }

        public Criteria andNodePathNotEqualTo(String value) {
            addCriterion("node_path <>", value, "nodePath");
            return (Criteria) this;
        }

        public Criteria andNodePathGreaterThan(String value) {
            addCriterion("node_path >", value, "nodePath");
            return (Criteria) this;
        }

        public Criteria andNodePathGreaterThanOrEqualTo(String value) {
            addCriterion("node_path >=", value, "nodePath");
            return (Criteria) this;
        }

        public Criteria andNodePathLessThan(String value) {
            addCriterion("node_path <", value, "nodePath");
            return (Criteria) this;
        }

        public Criteria andNodePathLessThanOrEqualTo(String value) {
            addCriterion("node_path <=", value, "nodePath");
            return (Criteria) this;
        }

        public Criteria andNodePathLike(String value) {
            addCriterion("node_path like", value, "nodePath");
            return (Criteria) this;
        }

        public Criteria andNodePathNotLike(String value) {
            addCriterion("node_path not like", value, "nodePath");
            return (Criteria) this;
        }

        public Criteria andNodePathIn(List<String> values) {
            addCriterion("node_path in", values, "nodePath");
            return (Criteria) this;
        }

        public Criteria andNodePathNotIn(List<String> values) {
            addCriterion("node_path not in", values, "nodePath");
            return (Criteria) this;
        }

        public Criteria andNodePathBetween(String value1, String value2) {
            addCriterion("node_path between", value1, value2, "nodePath");
            return (Criteria) this;
        }

        public Criteria andNodePathNotBetween(String value1, String value2) {
            addCriterion("node_path not between", value1, value2, "nodePath");
            return (Criteria) this;
        }

        public Criteria andNodeValueIsNull() {
            addCriterion("node_value is null");
            return (Criteria) this;
        }

        public Criteria andNodeValueIsNotNull() {
            addCriterion("node_value is not null");
            return (Criteria) this;
        }

        public Criteria andNodeValueEqualTo(String value) {
            addCriterion("node_value =", value, "nodeValue");
            return (Criteria) this;
        }

        public Criteria andNodeValueNotEqualTo(String value) {
            addCriterion("node_value <>", value, "nodeValue");
            return (Criteria) this;
        }

        public Criteria andNodeValueGreaterThan(String value) {
            addCriterion("node_value >", value, "nodeValue");
            return (Criteria) this;
        }

        public Criteria andNodeValueGreaterThanOrEqualTo(String value) {
            addCriterion("node_value >=", value, "nodeValue");
            return (Criteria) this;
        }

        public Criteria andNodeValueLessThan(String value) {
            addCriterion("node_value <", value, "nodeValue");
            return (Criteria) this;
        }

        public Criteria andNodeValueLessThanOrEqualTo(String value) {
            addCriterion("node_value <=", value, "nodeValue");
            return (Criteria) this;
        }

        public Criteria andNodeValueLike(String value) {
            addCriterion("node_value like", value, "nodeValue");
            return (Criteria) this;
        }

        public Criteria andNodeValueNotLike(String value) {
            addCriterion("node_value not like", value, "nodeValue");
            return (Criteria) this;
        }

        public Criteria andNodeValueIn(List<String> values) {
            addCriterion("node_value in", values, "nodeValue");
            return (Criteria) this;
        }

        public Criteria andNodeValueNotIn(List<String> values) {
            addCriterion("node_value not in", values, "nodeValue");
            return (Criteria) this;
        }

        public Criteria andNodeValueBetween(String value1, String value2) {
            addCriterion("node_value between", value1, value2, "nodeValue");
            return (Criteria) this;
        }

        public Criteria andNodeValueNotBetween(String value1, String value2) {
            addCriterion("node_value not between", value1, value2, "nodeValue");
            return (Criteria) this;
        }

        public Criteria andOrderNumIsNull() {
            addCriterion("order_num is null");
            return (Criteria) this;
        }

        public Criteria andOrderNumIsNotNull() {
            addCriterion("order_num is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNumEqualTo(Integer value) {
            addCriterion("order_num =", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotEqualTo(Integer value) {
            addCriterion("order_num <>", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThan(Integer value) {
            addCriterion("order_num >", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_num >=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThan(Integer value) {
            addCriterion("order_num <", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThanOrEqualTo(Integer value) {
            addCriterion("order_num <=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumIn(List<Integer> values) {
            addCriterion("order_num in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotIn(List<Integer> values) {
            addCriterion("order_num not in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumBetween(Integer value1, Integer value2) {
            addCriterion("order_num between", value1, value2, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotBetween(Integer value1, Integer value2) {
            addCriterion("order_num not between", value1, value2, "orderNum");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}