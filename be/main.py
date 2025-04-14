from flask import Flask, request, jsonify
import numpy as np
import random
from tabulate import tabulate

# Khởi tạo ứng dụng Flask
app = Flask(__name__)

class BattleshipAI:
    def __init__(self, board_size=10, simulations=100):
        """
        Khởi tạo AI cho trò chơi Battleship
        
        :param board_size: Kích thước của bảng game (mặc định là 10x10)
        :param simulations: Số lượng mô phỏng MCTS (mặc định là 100)
        """
        self.board_size = board_size
        self.simulations = simulations

    def get_optimal_shot(self, board, ship_lengths):
        """
        Xác định nước đi tối ưu dựa trên trạng thái hiện tại của bảng
        
        :param board: Ma trận bảng game hiện tại
        :param ship_lengths: Danh sách độ dài các tàu còn lại
        :return: Tọa độ nước bắn tối ưu [x, y]
        """
        board_np = np.array(board)
        # Khởi tạo ma trận xác suất
        probability_matrix = self.initialize_probability_matrix(board_np)
        # Cập nhật ma trận xác suất dựa trên trạng thái hiện tại
        probability_matrix = self.update_probability_matrix(board_np, ship_lengths, probability_matrix)

        print("Ma trận xác suất trước MCTS:")
        print(tabulate(probability_matrix, tablefmt="grid"))

        # Tìm nước đi tốt nhất bằng thuật toán MCTS
        best_move = self.mcts(board_np, ship_lengths, probability_matrix)
        return best_move

    def initialize_probability_matrix(self, board_np):
        """
        Khởi tạo ma trận xác suất ban đầu
        
        :param board_np: Ma trận bảng game dạng numpy
        :return: Ma trận xác suất khởi tạo
        """
        # Khởi tạo ma trận với xác suất đồng đều cho các ô trống
        probability_matrix = np.ones_like(board_np, dtype=float) / np.sum(board_np == 0)
        # Đặt xác suất 0 cho các ô đã bắn trượt (-1) và ô đã chìm (2)
        probability_matrix[board_np == -1] = 0
        probability_matrix[board_np == 2] = 0
        return probability_matrix

    def update_probability_matrix(self, board_np, ship_lengths, probability_matrix):
        """
        Cập nhật ma trận xác suất dựa trên các trạng thái bảng hiện tại
        
        :param board_np: Ma trận bảng game
        :param ship_lengths: Độ dài các tàu còn lại
        :param probability_matrix: Ma trận xác suất cần cập nhật
        :return: Ma trận xác suất đã cập nhật
        """
        # Tìm các vị trí đã bắn trúng (giá trị 1)
        hits = np.where(board_np == 1)
        # Tăng xác suất xung quanh các vị trí trúng
        for x, y in zip(hits[0], hits[1]):
            probability_matrix = self.increase_probability_around_hit(probability_matrix, x, y, board_np, ship_lengths)

        # Áp dụng chiến thuật bắn cách quãng nếu còn tàu
        if ship_lengths:
            min_length = min(ship_lengths)
            probability_matrix = self.apply_adaptive_intermittent_shooting(probability_matrix, board_np, min_length)
        return probability_matrix

    def increase_probability_around_hit(self, probability_matrix, x, y, board_np, ship_lengths):
        """
        Tăng xác suất cho các ô xung quanh vị trí đã trúng
        
        :param probability_matrix: Ma trận xác suất cần cập nhật
        :param x, y: Tọa độ vị trí đã trúng
        :param board_np: Ma trận bảng game
        :param ship_lengths: Độ dài các tàu còn lại
        :return: Ma trận xác suất đã cập nhật
        """
        rows, cols = board_np.shape
        if not ship_lengths:
            return probability_matrix

        min_length = min(ship_lengths)
        increase_amount = 0.2  # Giá trị tăng xác suất

        # Kiểm tra phương ngang
        can_extend_left = True
        for i in range(1, min_length):
            if x - i < 0 or board_np[x - i, y] != 0:
                can_extend_left = False
                break
        can_extend_right = True
        for i in range(1, min_length):
            if x + i >= rows or board_np[x + i, y] != 0:
                can_extend_right = False
                break

        # Tăng xác suất cho các ô bên trái nếu có thể
        if can_extend_left:
            for i in range(1, min_length):
                probability_matrix[x - i, y] += increase_amount

        # Tăng xác suất cho các ô bên phải nếu có thể
        if can_extend_right:
            for i in range(1, min_length):
                probability_matrix[x + i, y] += increase_amount

        # Kiểm tra phương dọc
        can_extend_up = True
        for i in range(1, min_length):
            if y - i < 0 or board_np[x, y - i] != 0:
                can_extend_up = False
                break
        can_extend_down = True
        for i in range(1, min_length):
            if y + i >= cols or board_np[x, y + i] != 0:
                can_extend_down = False
                break

        # Tăng xác suất cho các ô phía trên nếu có thể
        if can_extend_up:
            for i in range(1, min_length):
                probability_matrix[x, y - i] += increase_amount

        # Tăng xác suất cho các ô phía dưới nếu có thể
        if can_extend_down:
            for i in range(1, min_length):
                probability_matrix[x, y + i] += increase_amount

        return probability_matrix

    def apply_adaptive_intermittent_shooting(self, probability_matrix, board_np, min_length):
        """
        Áp dụng chiến thuật bắn cách quãng thích ứng
        
        :param probability_matrix: Ma trận xác suất
        :param board_np: Ma trận bảng game
        :param min_length: Độ dài tàu nhỏ nhất còn lại
        :return: Ma trận xác suất đã cập nhật
        """
        rows, cols = board_np.shape
        spacing = min_length - 1
        hits = np.where(board_np == 1)

        # Nếu chưa có lần trúng nào, áp dụng chiến thuật cách quãng đều
        if not hits[0].size:
            for i in range(rows):
                for j in range(cols):
                    if (i + j) % (spacing + 1) != 0:
                        probability_matrix[i, j] *= 0.5
        # Nếu đã có lần trúng, điều chỉnh xác suất quanh vị trí trúng
        else:
            for x, y in zip(hits[0], hits[1]):
                for i in range(max(0, x - spacing - 1), min(rows, x + spacing + 2)):
                    for j in range(max(0, y - spacing - 1), min(cols, y + spacing + 2)):
                        if (i + j) % (spacing + 1) != 0:
                            probability_matrix[i, j] *= 0.7

        return probability_matrix

    def mcts(self, board_np, ship_lengths, probability_matrix):
        """
        Thuật toán Monte Carlo Tree Search để tìm nước đi tối ưu
        
        :param board_np: Ma trận bảng game
        :param ship_lengths: Độ dài các tàu còn lại
        :param probability_matrix: Ma trận xác suất
        :return: Tọa độ nước đi tốt nhất [x, y]
        """
        rows, cols = board_np.shape
        
        # Tìm giá trị xác suất cao nhất
        max_prob = np.max(probability_matrix)
        
        # Tìm tất cả vị trí có xác suất cao nhất
        max_indices = np.where(probability_matrix == max_prob)
        
        # Tạo danh sách các vị trí có xác suất cao nhất
        best_positions = list(zip(max_indices[0], max_indices[1]))
        
        # Chọn ngẫu nhiên một trong các vị trí có xác suất cao nhất
        chosen_position = random.choice(best_positions)
        best_move = list(chosen_position)
        
        # Lấy giá trị xác suất của nước đi được chọn
        score = probability_matrix[best_move[0], best_move[1]]
        
        print(f"Nước đi tốt nhất được chọn: {best_move} với xác suất: {score}")
        print(f"Số lượng vị trí có xác suất cao nhất: {len(best_positions)}")
        
        return best_move

    def select_untested_move(self, board_np, probability_matrix):
        """
        Chọn một nước đi chưa thử dựa trên ma trận xác suất
        
        :param board_np: Ma trận bảng game
        :param probability_matrix: Ma trận xác suất
        :return: Tọa độ nước đi được chọn [x, y]
        """
        rows, cols = board_np.shape
        available_moves = np.where(board_np == 0)
        if not available_moves[0].size:
            return None, None

        # Chuẩn hóa xác suất
        probabilities = probability_matrix[available_moves]
        probabilities /= np.sum(probabilities)

        # Chọn ngẫu nhiên dựa trên phân phối xác suất
        move_index = np.random.choice(len(available_moves[0]), p=probabilities)
        x, y = available_moves[0][move_index], available_moves[1][move_index]
        return x, y

    def simulate_move(self, board_np, x, y, ship_lengths):
        """
        Mô phỏng kết quả của một nước đi
        (Đây chỉ là hàm giả lập, trong trò chơi thực tế sẽ kiểm tra bắn trúng hay trượt)
        
        :param board_np: Ma trận bảng game
        :param x, y: Tọa độ nước bắn
        :param ship_lengths: Độ dài các tàu còn lại
        :return: 1 nếu trúng, -1 nếu trượt
        """
        if random.random() < 0.5:
            board_np[x, y] = 1
            return 1
        else:
            board_np[x, y] = -1
            return -1

# Định nghĩa API endpoint
@app.route('/get_shot', methods=['POST'])
def get_shot():
    """
    API endpoint để nhận nước đi tối ưu từ AI
    
    :return: JSON chứa tọa độ nước bắn tối ưu
    """
    data = request.get_json()
    board = data['board']
    ship_lengths = data['ship_lengths']

    # Tạo thể hiện của AI và lấy nước đi tối ưu
    ai = BattleshipAI()
    optimal_shot = ai.get_optimal_shot(board, ship_lengths)
    optimal_shot = [int(x) for x in optimal_shot]

    return jsonify({'optimal_shot': optimal_shot})

# Chạy ứng dụng Flask
if __name__ == '__main__':
    app.run(debug=True, port=5000)