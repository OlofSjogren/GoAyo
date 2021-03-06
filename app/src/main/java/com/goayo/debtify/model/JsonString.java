package com.goayo.debtify.model;

/**
 * @author Oscar Sanner
 * @date 2020-10-12
 *
 * A class used for holding differently formatted json-stings.
 * Each class has the same method but every class instantiating the internal classes
 * must pay attention to how the strings are required to be formatted.
 *
 * 2020-10-14 Modified by Oscar Sanner: Added documentation.
 */

public abstract class JsonString {

    private final String json;

    public JsonString(String json) {
        this.json = json;
    }

    public String getJson() {
        return json;
    }

    /**
     * A class holding a group json string. The string must have the following
     * format:
     *
     *    {
     *       "name": A String,
     *       "date": A String,
     *       "id": A String,
     *       "members": An array with objects of the following JSON-Strings:
     *             {
     *             "name": A String,
     *             "phonenumber": A String,
     *             "password": A String,
     *             "contacts": An array of Strings.
     *              }
     *        "debts": An array with objects of the following JSON-Strings:
     *             {
     *             "lender": An an object with the following JSON-Strings
     *                  {
     *                  "name": A String,
     *                  "phonenumber": A String,
     *                  "password": A String,
     *                  "contacts": An array of Strings.
     *                  }
     *              "borrower": An an object with the following JSON-Strings
     *                  {
     *                  "name": A String,
     *                  "phonenumber": A String,
     *                  "password": A String,
     *                  "contacts": An array of Strings.
     *                  }
     *              "owed": A String,
     *              "id": A String,
     *              "payments": borrower: An an object with the following JSON-Strings
     *                  {
     *                  "amount": A String,
     *                  "id": A String,
     *                  }
     *              "description": A String
     *             }
     *         }
     *    }
     *
     */
    public static class GroupArrayJsonString extends JsonString {
        public GroupArrayJsonString(String json) {
            super(json);
        }
    }

    /**
     * A class holding a User array json string. The string must have the following
     * format:
     *
     *      {
     *          contacts: An array of JSON objects with the following properties:
     *
     *             "name": A String,
     *             "phonenumber": A String,
     *             "password": A String,
     *             "contacts": An array of Strings.
     *          }
     *      }
     */
    public static class UserArrayJsonString extends JsonString{
        public UserArrayJsonString(String json) {
            super(json);
        }
    }

    /**
     * A class holding a User json string. The string must have the following
     * format:
     *
     *          {
     *             "name": A String,
     *             "phonenumber": A String,
     *             "password": A String,
     *             "contacts": An array of Strings.
     *          }
     */
    public static class UserJsonString extends JsonString{
        public UserJsonString(String json) {
            super(json);
        }
    }
}
