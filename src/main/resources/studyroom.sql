/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : studyroom

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 04/12/2022 21:41:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chat_log
-- ----------------------------
DROP TABLE IF EXISTS `chat_log`;
CREATE TABLE `chat_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sender` int NOT NULL,
  `receiver` int DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `text_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1: group chat; 2: private chat',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of chat_log
-- ----------------------------
BEGIN;
INSERT INTO `chat_log` VALUES (47, 2, 0, '2022-12-03 13:31:36', '小黑子', 1);
INSERT INTO `chat_log` VALUES (48, 3, 0, '2022-12-03 13:31:43', '？？', 1);
INSERT INTO `chat_log` VALUES (49, 4, 0, '2022-12-03 13:32:12', '露出鸡脚了吧', 1);
INSERT INTO `chat_log` VALUES (50, 6, 0, '2022-12-03 13:39:23', '纯路人感觉完全没必要', 1);
INSERT INTO `chat_log` VALUES (51, 5, 0, '2022-12-03 13:40:16', 'hahahaha', 1);
INSERT INTO `chat_log` VALUES (52, 7, 0, '2022-12-03 13:42:07', 'What day is it today?', 1);
INSERT INTO `chat_log` VALUES (53, 8, 0, '2022-12-03 13:42:45', 'Friday?', 1);
INSERT INTO `chat_log` VALUES (54, 9, 0, '2022-12-03 13:44:43', 'Today is Saturday', 1);
INSERT INTO `chat_log` VALUES (55, 10, 0, '2022-12-03 13:49:34', 'Your hairstyle looks so good', 1);
INSERT INTO `chat_log` VALUES (56, 9, 0, '2022-12-03 13:49:50', 'Thanks!', 1);
INSERT INTO `chat_log` VALUES (57, 8, 0, '2022-12-03 15:31:32', 'just a test', 1);
INSERT INTO `chat_log` VALUES (58, 8, 0, '2022-12-03 15:43:38', 'e', 1);
INSERT INTO `chat_log` VALUES (59, 3, 4, '2022-12-03 15:55:59', 'hello', 2);
INSERT INTO `chat_log` VALUES (60, 3, 4, '2022-12-03 15:56:33', 'hello', 2);
INSERT INTO `chat_log` VALUES (61, 2, 4, '2022-12-03 16:11:11', 'hii', 2);
INSERT INTO `chat_log` VALUES (62, 5, 7, '2022-12-03 16:11:58', 'Hi, Elon', 2);
INSERT INTO `chat_log` VALUES (63, 8, 2, '2022-12-03 16:12:22', 'Hi, Taylor', 2);
INSERT INTO `chat_log` VALUES (64, 4, 2, '2022-12-03 16:12:37', 'Hiii, Taylor', 2);
INSERT INTO `chat_log` VALUES (65, 6, 2, '2022-12-03 16:12:51', 'How are you, Taylor', 2);
INSERT INTO `chat_log` VALUES (66, 4, 3, '2022-12-03 21:03:56', 'halo, emilia', 2);
INSERT INTO `chat_log` VALUES (69, 3, 4, '2022-12-03 21:05:02', 'hi', 2);
INSERT INTO `chat_log` VALUES (73, 3, 2, '2022-12-03 21:14:39', 'Hi, taylor', 2);
INSERT INTO `chat_log` VALUES (74, 4, 0, '2022-12-03 22:52:50', 'a', 1);
INSERT INTO `chat_log` VALUES (75, 4, 2, '2022-12-03 22:53:33', 'test', 2);
INSERT INTO `chat_log` VALUES (76, 10, 0, '2022-12-04 20:20:05', 'halo', 1);
INSERT INTO `chat_log` VALUES (77, 2, 0, '2022-12-04 20:27:39', 'hii', 1);
INSERT INTO `chat_log` VALUES (78, 4, 0, '2022-12-04 21:29:50', 'halo', 1);
INSERT INTO `chat_log` VALUES (79, 10, 3, '2022-12-04 21:34:24', 'Hi, I am emilia', 2);
INSERT INTO `chat_log` VALUES (80, 10, 3, '2022-12-04 21:34:32', 'Hi, I am tcefrep', 2);
COMMIT;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mid` int DEFAULT NULL,
  `uid` int DEFAULT NULL,
  `content` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of comment
-- ----------------------------
BEGIN;
INSERT INTO `comment` VALUES (1, 1, 2, 'Good night', '2022-12-04 11:45:07');
INSERT INTO `comment` VALUES (2, 1, 6, 'Good night +1', '2022-12-04 11:46:09');
INSERT INTO `comment` VALUES (4, 4, 2, 'ohhh', '2022-12-04 12:22:13');
INSERT INTO `comment` VALUES (6, 4, 2, 'ff', '2022-12-04 12:31:26');
INSERT INTO `comment` VALUES (7, 5, 2, 'Your hairstyle looks so cool!', '2022-12-04 12:32:55');
INSERT INTO `comment` VALUES (11, 6, 6, 'hhh', '2022-12-04 12:43:37');
INSERT INTO `comment` VALUES (12, 7, 8, 'I can\'t agree with you more!', '2022-12-04 12:47:48');
INSERT INTO `comment` VALUES (13, 8, 6, 'cool', '2022-12-04 12:48:54');
INSERT INTO `comment` VALUES (26, 7, 7, 'Beautiful!', '2022-12-04 12:56:00');
INSERT INTO `comment` VALUES (27, 6, 2, 'hhhhh', '2022-12-04 20:18:49');
COMMIT;

-- ----------------------------
-- Table structure for distribution
-- ----------------------------
DROP TABLE IF EXISTS `distribution`;
CREATE TABLE `distribution` (
  `uid` varchar(5) NOT NULL,
  `field` varchar(45) NOT NULL,
  `score` int DEFAULT NULL,
  PRIMARY KEY (`uid`,`field`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of distribution
-- ----------------------------
BEGIN;
INSERT INTO `distribution` VALUES ('2', 'cs', 10);
INSERT INTO `distribution` VALUES ('2', 'is', 10);
INSERT INTO `distribution` VALUES ('2', 'nlp', 8);
INSERT INTO `distribution` VALUES ('2', 'ses', 28);
INSERT INTO `distribution` VALUES ('456', 'cs', 20);
INSERT INTO `distribution` VALUES ('456', 'is', 15);
INSERT INTO `distribution` VALUES ('456', 'ses', 8);
COMMIT;

-- ----------------------------
-- Table structure for favorites
-- ----------------------------
DROP TABLE IF EXISTS `favorites`;
CREATE TABLE `favorites` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int DEFAULT NULL,
  `mid` int DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of favorites
-- ----------------------------
BEGIN;
INSERT INTO `favorites` VALUES (7, 1, 8, '2022-12-02 10:38:31');
INSERT INTO `favorites` VALUES (10, 2, 5, '2022-12-02 10:45:44');
INSERT INTO `favorites` VALUES (14, 2, 2, '2022-12-02 10:46:07');
INSERT INTO `favorites` VALUES (15, 2, 1, '2022-12-02 10:46:08');
INSERT INTO `favorites` VALUES (17, 2, 3, '2022-12-03 18:34:53');
INSERT INTO `favorites` VALUES (18, 2, 8, '2022-12-03 18:34:56');
INSERT INTO `favorites` VALUES (20, 3, 5, '2022-12-03 22:53:50');
INSERT INTO `favorites` VALUES (21, 4, 4, '2022-12-04 21:35:52');
COMMIT;

-- ----------------------------
-- Table structure for moments
-- ----------------------------
DROP TABLE IF EXISTS `moments`;
CREATE TABLE `moments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `likes` int NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cover` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of moments
-- ----------------------------
BEGIN;
INSERT INTO `moments` VALUES (1, 8, 'It\'s time to sleep', 19, '2022-12-01 14:08:34', 'images/moments/cover/sleep.png');
INSERT INTO `moments` VALUES (2, 2, 'My Dream!', 1, '2022-12-01 15:31:30', 'images/moments/cover/robot.png');
INSERT INTO `moments` VALUES (3, 4, 'Mom! I am so scared', 4, '2022-12-01 16:52:19', 'images/moments/cover/devil.png');
INSERT INTO `moments` VALUES (4, 7, 'I hope you guys can study hard, otherwise ...', 3, '2022-12-01 20:12:12', 'images/moments/cover/fire.png');
INSERT INTO `moments` VALUES (5, 3, 'I have studied 10 hours today, I feel better!!', 13, '2022-12-01 16:56:52', 'images/moments/cover/ikun.png');
INSERT INTO `moments` VALUES (6, 5, 'A good day starts with leetcode 2235', 3, '2022-12-01 19:36:27', 'images/moments/cover/lc2235.png');
INSERT INTO `moments` VALUES (7, 6, 'You reap what you sow!', 9, '2022-12-01 19:51:14', 'images/moments/cover/classroom.png');
INSERT INTO `moments` VALUES (8, 4, 'Daily Attendance', 2, '2022-12-01 16:55:44', 'images/moments/cover/attendence.png');
INSERT INTO `moments` VALUES (9, 2, 'I feel hopeless. What should I do?', 5, '2022-12-01 20:46:49', 'images/moments/cover/notes.png');
COMMIT;

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `rid` int NOT NULL,
  `tid` int NOT NULL,
  `uid` varchar(45) NOT NULL,
  `field` varchar(45) NOT NULL,
  PRIMARY KEY (`rid`,`tid`,`field`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of reply
-- ----------------------------
BEGIN;
INSERT INTO `reply` VALUES (1, 1, '2', 'cs');
INSERT INTO `reply` VALUES (1, 1, '2', 'is');
INSERT INTO `reply` VALUES (1, 1, '2', 'ses');
INSERT INTO `reply` VALUES (1, 2, '2', 'cs');
INSERT INTO `reply` VALUES (1, 2, '2', 'ses');
INSERT INTO `reply` VALUES (1, 3, '2', 'cs');
INSERT INTO `reply` VALUES (1, 3, '2', 'ses');
INSERT INTO `reply` VALUES (2, 1, '2', 'cs');
INSERT INTO `reply` VALUES (2, 2, '2', 'ses');
COMMIT;

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `uid` varchar(5) NOT NULL,
  `accumulation` int DEFAULT NULL,
  `reputation` int DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of score
-- ----------------------------
BEGIN;
INSERT INTO `score` VALUES ('2', 12, 29);
INSERT INTO `score` VALUES ('456', 20, 9);
COMMIT;

-- ----------------------------
-- Table structure for seat
-- ----------------------------
DROP TABLE IF EXISTS `seat`;
CREATE TABLE `seat` (
  `id` int NOT NULL,
  `is_sit_down` tinyint(1) DEFAULT NULL,
  `room_id` int NOT NULL,
  PRIMARY KEY (`id`,`room_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of seat
-- ----------------------------
BEGIN;
INSERT INTO `seat` VALUES (1, 1, 1);
INSERT INTO `seat` VALUES (2, 1, 1);
INSERT INTO `seat` VALUES (3, 0, 1);
INSERT INTO `seat` VALUES (4, 0, 1);
INSERT INTO `seat` VALUES (5, 0, 1);
INSERT INTO `seat` VALUES (6, 0, 1);
COMMIT;

-- ----------------------------
-- Table structure for studyroom
-- ----------------------------
DROP TABLE IF EXISTS `studyroom`;
CREATE TABLE `studyroom` (
  `id` int NOT NULL,
  `type_id` int DEFAULT NULL,
  `capacity` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of studyroom
-- ----------------------------
BEGIN;
INSERT INTO `studyroom` VALUES (1, 1, 24, '2022-11-18 21:57:36');
COMMIT;

-- ----------------------------
-- Table structure for studytime
-- ----------------------------
DROP TABLE IF EXISTS `studytime`;
CREATE TABLE `studytime` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int DEFAULT NULL,
  `room_id` int NOT NULL DEFAULT '1',
  `target_time` int DEFAULT NULL,
  `real_time` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  `is_finished` tinyint(1) DEFAULT NULL,
  `goal` varchar(255) NOT NULL DEFAULT 'study',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of studytime
-- ----------------------------
BEGIN;
INSERT INTO `studytime` VALUES (125, 1, 1, 60, 55, NULL, NULL, NULL, 'Read');
INSERT INTO `studytime` VALUES (126, 1, 1, 120, 60, NULL, NULL, NULL, 'Study');
INSERT INTO `studytime` VALUES (127, 1, 1, 60, 40, NULL, NULL, NULL, 'Work');
INSERT INTO `studytime` VALUES (128, 1, 1, 120, 80, NULL, NULL, NULL, 'Review');
INSERT INTO `studytime` VALUES (129, 1, 1, 120, 120, NULL, NULL, NULL, 'Rest');
COMMIT;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int DEFAULT NULL,
  `room_id` int NOT NULL DEFAULT '1',
  `create_time` datetime DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  `is_finished` tinyint(1) DEFAULT NULL,
  `goal` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'study',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of task
-- ----------------------------
BEGIN;
INSERT INTO `task` VALUES (1, 1, 1, NULL, NULL, 1, 'study');
INSERT INTO `task` VALUES (3, 1, 0, '2022-11-29 20:58:05', '2022-11-29 21:01:05', 1, 'study');
INSERT INTO `task` VALUES (4, 2, 1, '2022-11-29 21:10:46', '2022-11-29 21:43:46', 1, 'study');
INSERT INTO `task` VALUES (5, 2, 1, '2022-11-29 21:11:22', '2022-11-29 22:25:22', 1, 'study');
INSERT INTO `task` VALUES (6, 2, 1, '2022-11-29 21:12:36', '2022-11-29 21:44:36', 1, 'study');
INSERT INTO `task` VALUES (7, 13, 1, '2022-11-29 21:29:04', '2022-11-29 21:32:04', 0, 'study');
INSERT INTO `task` VALUES (8, 13, 1, '2022-11-29 21:29:10', '2022-11-29 21:32:10', 0, 'study');
INSERT INTO `task` VALUES (9, 13, 1, '2022-11-29 21:29:14', '2022-11-29 21:36:14', 0, 'study');
INSERT INTO `task` VALUES (10, 96, 1, '2022-11-29 21:30:37', '2022-11-29 21:32:37', 0, 'study');
INSERT INTO `task` VALUES (11, 21, 1, '2022-11-29 21:34:46', '2022-11-29 22:35:46', 1, 'study');
INSERT INTO `task` VALUES (12, 6, 1, '2022-11-29 21:35:39', '2022-11-29 21:55:39', 0, 'study');
INSERT INTO `task` VALUES (13, 27, 1, '2022-11-29 21:38:13', '2022-11-29 21:38:13', 0, 'study');
INSERT INTO `task` VALUES (14, 34, 1, '2022-11-29 21:40:24', '2022-11-29 21:42:24', 0, 'study');
INSERT INTO `task` VALUES (15, 9, 1, '2022-11-29 21:49:09', '2022-11-29 22:03:09', 0, 'study');
INSERT INTO `task` VALUES (16, 21, 1, '2022-11-30 22:16:17', '2022-11-30 22:30:17', 1, 'study');
INSERT INTO `task` VALUES (112, 64, 1, '2022-11-29 22:49:29', '2022-11-29 23:05:29', 0, 'study');
INSERT INTO `task` VALUES (113, 69, 1, '2022-11-29 22:50:11', '2022-11-29 23:12:11', 0, 'study');
INSERT INTO `task` VALUES (114, 27, 1, '2022-11-29 22:52:01', '2022-11-29 22:55:01', 0, 'study');
INSERT INTO `task` VALUES (115, 27, 1, '2022-11-29 22:52:01', '2022-11-29 22:55:01', 1, 'study');
INSERT INTO `task` VALUES (116, 30, 1, '2022-11-29 22:55:59', '2022-11-29 22:58:59', 0, 'study');
INSERT INTO `task` VALUES (117, 29, 1, '2022-11-29 22:58:27', '2022-11-29 22:58:27', 0, 'test goal');
INSERT INTO `task` VALUES (118, 29, 1, '2022-11-29 22:58:50', '2022-11-29 23:21:50', 0, 'test goal');
INSERT INTO `task` VALUES (119, 29, 1, '2022-11-29 22:59:15', '2022-11-29 23:00:15', 1, 'test goal');
INSERT INTO `task` VALUES (120, 52, 1, '2022-11-29 23:01:31', '2022-11-29 23:04:31', 1, 'read');
INSERT INTO `task` VALUES (121, 23, 1, '2022-11-29 23:06:39', '2022-11-29 23:10:39', 0, 'work');
INSERT INTO `task` VALUES (122, 97, 1, '2022-11-30 12:39:48', '2022-11-30 13:05:48', 1, 'work');
INSERT INTO `task` VALUES (123, 4, 1, '2022-11-30 13:49:33', '2022-11-30 14:03:33', 0, 'study');
INSERT INTO `task` VALUES (124, 96, 1, '2022-11-30 19:23:35', '2022-11-30 19:25:35', 1, 'work');
INSERT INTO `task` VALUES (125, 0, 1, '2022-12-04 19:34:23', '2022-12-04 20:24:23', 0, 'study');
INSERT INTO `task` VALUES (126, 0, 1, '2022-12-04 19:35:15', '2022-12-04 20:35:15', 0, 'study');
INSERT INTO `task` VALUES (127, 0, 1, '2022-12-04 19:37:15', '2022-12-04 20:34:15', 0, 'study');
INSERT INTO `task` VALUES (128, 0, 1, '2022-12-04 19:40:26', '2022-12-04 20:25:26', 0, 'study');
INSERT INTO `task` VALUES (129, 4, 1, '2022-12-04 21:34:59', '2022-12-04 21:54:59', 0, 'study');
COMMIT;

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic` (
  `tid` int NOT NULL,
  `field` varchar(45) NOT NULL,
  `host` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`tid`,`field`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of topic
-- ----------------------------
BEGIN;
INSERT INTO `topic` VALUES (1, 'cs', '123');
INSERT INTO `topic` VALUES (1, 'is', NULL);
INSERT INTO `topic` VALUES (1, 'ses', '123');
INSERT INTO `topic` VALUES (2, 'cs', '123');
INSERT INTO `topic` VALUES (2, 'ses', '123');
INSERT INTO `topic` VALUES (3, 'cs', '123');
INSERT INTO `topic` VALUES (3, 'ses', '123');
COMMIT;

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type` (
  `id` int NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of type
-- ----------------------------
BEGIN;
INSERT INTO `type` VALUES (1, 'Computer Science');
INSERT INTO `type` VALUES (2, 'Science');
INSERT INTO `type` VALUES (3, 'Arts');
INSERT INTO `type` VALUES (4, 'Math');
INSERT INTO `type` VALUES (5, 'Business');
INSERT INTO `type` VALUES (6, 'Architechture');
INSERT INTO `type` VALUES (7, 'Chemistry');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(256) NOT NULL,
  `password` varchar(256) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `username` varchar(256) NOT NULL,
  `createtime` date DEFAULT NULL,
  `avatar` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'lzl', '123456', 22, 'Male', 'null', NULL, 'jacob', '2022-11-17', 'images/avatar/avatar2.png');
INSERT INTO `user` VALUES (2, 'taylor', '123456', 30, 'Female', NULL, NULL, 'Taylor', '2022-11-16', 'images/avatar/taylor.png');
INSERT INTO `user` VALUES (3, 'emilia', '123456', 35, 'Female', NULL, NULL, 'Emilia', '2022-12-01', 'images/avatar/emilia.png');
INSERT INTO `user` VALUES (4, 'zee', '123456', 45, 'Male', NULL, NULL, 'ZeeTao', '2022-12-01', 'images/avatar/avatar3.png');
INSERT INTO `user` VALUES (5, 'lisa', '123456', 25, 'Female', NULL, NULL, 'Lisa', '2022-12-01', 'images/avatar/lisa.png');
INSERT INTO `user` VALUES (6, 'ikun', '123456', 22, 'Male', NULL, NULL, 'Ikun', '2022-12-01', 'images/avatar/avatar1.png');
INSERT INTO `user` VALUES (7, 'elon', '123456', 50, 'Male', NULL, NULL, 'Elon', '2022-12-01', 'images/avatar/elon.png');
INSERT INTO `user` VALUES (8, 'lazyGoat', '123456', 20, 'Male', NULL, NULL, 'Lazy Goat', '2022-12-01', 'images/avatar/lazyGoat.png');
INSERT INTO `user` VALUES (9, 'gg', '123456', 19, 'Male', NULL, NULL, 'GG', '2022-12-03', 'images/avatar/avatar4.png');
INSERT INTO `user` VALUES (10, 'tcefrep', '123456', 22, 'Male', NULL, NULL, 'Tcefre', '2022-12-03', 'images/avatar/avatar6.png');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
